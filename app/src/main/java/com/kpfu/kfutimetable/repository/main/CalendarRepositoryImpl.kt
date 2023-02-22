package com.kpfu.kfutimetable.repository.main

import android.content.Context
import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarState
import com.kpfu.kfutimetable.presentation.mainscreen.entities.Lesson
import com.kpfu.kfutimetable.repository.main.dto.LessonsDto
import com.kpfu.kfutimetable.repository.main.dto.SubjectDto
import com.kpfu.kfutimetable.utils.ResultState
import com.kpfu.kfutimetable.utils.filterByDay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.await
import java.time.DayOfWeek
import java.time.Month
import javax.inject.Inject

class CalendarRepositoryImpl @Inject constructor(
    private val context: Context,
    private val calendarWebService: CalendarWebService,
) : CalendarRepository {

    override fun getLessonsByDay(date: String): Flow<ResultState<CalendarState>> =
        flow {
            emit(ResultState(isLoading = true))

            val data: SubjectDto
            try {
                data = calendarWebService.getLessonsByDay(date).await()
            } catch (e: HttpException) {
                emit(ResultState(error = e))
                return@flow
            }

            error("Not yet implemented")
            emit(ResultState(data = CalendarState(listOf())))
        }

    override fun getLessonsForWeek(): Flow<ResultState<List<CalendarState>>> = flow {
        emit(ResultState(isLoading = true))

        val data: LessonsDto
        try {
            data = calendarWebService.getLessonsForWeek().await()
        } catch (e: HttpException) {
            emit(ResultState(error = e))
            return@flow
        }

        val lessonsForWeek: List<CalendarState> = mutableListOf<CalendarState>().apply {
            DayOfWeek.values().forEach {
                add(CalendarState(data.filterByDay(it, context)))
            }
        }

        emit(ResultState(data = lessonsForWeek))
    }

    override fun getCurrentMonths(): Flow<ResultState<List<Month>>> = flow {

    }
}