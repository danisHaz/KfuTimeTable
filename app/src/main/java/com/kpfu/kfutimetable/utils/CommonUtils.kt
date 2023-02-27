package com.kpfu.kfutimetable.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.kpfu.kfutimetable.R
import com.kpfu.kfutimetable.di.base.DateFormatterAnnotations
import com.kpfu.kfutimetable.presentation.mainscreen.entities.Lesson
import com.kpfu.kfutimetable.repository.main.dto.LessonsDto
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.*
import javax.inject.Inject

val BASE_URL = "http://192.168.114.241:5050/"

data class LocalDateWrapper<AdditionalParam>(
    val date: LocalDate,
    val param: AdditionalParam
)

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "UserSession")

val term1 = listOf(Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER)
val term2 = listOf(Month.FEBRUARY, Month.MARCH, Month.APRIL, Month.MAY)
val firstDayOfTerm1 = LocalDate.of(LocalDate.now().year, Month.SEPTEMBER, 1)
val firstDayOfTerm2 = LocalDate.of(LocalDate.now().year, Month.FEBRUARY, 9)

class TimetableDataHolder(
    private val lessons: LessonsDto,
    private val formatter: DateTimeFormatter,
    private val monthNamesList: List<String>
) {

    fun filterByDate(date: LocalDate): List<Lesson> {
        val firstDayOfCurrentTerm = if (getCurrentTerm(date) == 0) {
            firstDayOfTerm1
        } else {
            firstDayOfTerm2
        }

        val evenWeek = lessons.lessonsForEvenWeek.filter {
            getDayOfWeekFrom(it.day) == date.dayOfWeek && matchesAdditionalWeekFilters(
                date, firstDayOfCurrentTerm, it.additionalWeekFilters
            )
        }.map { it.toLesson() }

        val oddWeek = lessons.lessonsForOddWeek.filter {
            getDayOfWeekFrom(it.day) == date.dayOfWeek && matchesAdditionalWeekFilters(
                date, firstDayOfCurrentTerm, it.additionalWeekFilters
            )
        }.map { it.toLesson() }

        return when (getTypeOfWeek(date, firstDayOfCurrentTerm)) {
            1 -> oddWeek
            else -> evenWeek
        }
    }

    private fun getDayOfWeekFrom(dayOfWeek: String): DayOfWeek {
        monthNamesList.forEachIndexed { ind, day ->
            if (day.lowercase() == dayOfWeek.lowercase()) {
                return DayOfWeek.of(ind + 1)
            }
        }

        error("dayOfWeek is inappropriate: $dayOfWeek")
    }

    private fun getCurrentTerm(date: LocalDate) = when (date.month) {
        in term1 -> 0
        else -> 1
    }

    private fun getTypeOfWeek(day: LocalDate, firstDayOfSemestr: LocalDate): Int {
        val weekFields = WeekFields.of(Locale.getDefault())
        val weekNumberForCurDay = day.get(weekFields.weekOfWeekBasedYear())
        val weekNumberForFirstDay = firstDayOfSemestr.get(weekFields.weekOfWeekBasedYear())
        return (weekNumberForCurDay - weekNumberForFirstDay + 1) % 2
    }

    private fun matchesAdditionalWeekFilters(
        date: LocalDate,
        firstDay: LocalDate,
        filters: List<String>
    ): Boolean {
        if (filters.isEmpty()) {
            return true
        }

        var matches = true
        filters.forEach { filter ->
            kotlin.runCatching {
                if (filter.contains(".")) {
                    val (fromDate, toDate) = filter.split('-').map {
                        LocalDate.parse(it, formatter)
                    }

                    if (fromDate > date || date > toDate) {
                        matches = false
                    }
                } else {
                    val (fromWeek, toWeek) = filter.split(' ')[0].split('-').map {
                        it.toInt()
                    }
                    val weekFields = WeekFields.of(Locale.getDefault())
                    val currentWeekNumber = date.get(weekFields.weekOfWeekBasedYear())
                    val firstDayWeekNumber = firstDay.get(weekFields.weekOfWeekBasedYear())
                    (currentWeekNumber - firstDayWeekNumber + 1).let {
                        if (fromWeek > it || it > toWeek) {
                            matches = false
                        }
                    }
                }
            }
        }

        return matches
    }
}