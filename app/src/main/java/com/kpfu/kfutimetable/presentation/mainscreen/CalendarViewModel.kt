package com.kpfu.kfutimetable.presentation.mainscreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kpfu.kfutimetable.presentation.base.BaseViewModel
import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarState
import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarViewState
import com.kpfu.kfutimetable.repository.main.CalendarRepository
import com.kpfu.kfutimetable.utils.LocalDateWrapper
import com.kpfu.kfutimetable.utils.UserSession
import com.kpfu.kfutimetable.utils.term1
import com.kpfu.kfutimetable.utils.term2
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val calendarRepository: CalendarRepository,
    private val formatter: DateTimeFormatter
) : BaseViewModel<CalendarState, CalendarViewState>(
    initialState = {
        CalendarState(
            lessons = listOf()
        )
    },
    viewStateMapper = CalendarViewStateMapper()
) {

    val monthListData: MutableLiveData<List<Month>> = MutableLiveData(listOf())

    var lessonsForWeek: List<CalendarState>? = null

    var currentDayData: LocalDate? = null
    val dayItemCarouselData: MutableLiveData<List<LocalDateWrapper<Boolean>>> =
        MutableLiveData(listOf())

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isError: MutableLiveData<Boolean> = MutableLiveData(false)
    private var monthListJob: Job? = null
    private var lessonListJob: Job? = null

    fun getCurrentMonthsList() {
        val day = LocalDate.now()
        monthListData.value = when (day.month) {
            in term1.plusElement(Month.JANUARY) -> term1
            else -> term2
        }
    }

    fun initDayItemCarousel(months: List<Month>) {
        setCurrentDay(months)
        dayItemCarouselData.value = inflateDateForMonth(currentDayData)
    }

    fun updateDayItemCarousel(month: Month) {
        setNearestDay(month)
        dayItemCarouselData.value = inflateDateForMonth(currentDayData)
    }

    fun getLessonsOnDay(desiredDay: Int, desiredMonth: Month) {
        currentDayData = LocalDate.now().withMonth(desiredMonth.value).withDayOfMonth(desiredDay)
        val updateState: ((List<CalendarState>) -> Unit) = { lessons ->
            currentDayData?.let { currentDay ->
                state = if (lessons.size >= currentDay.dayOfWeek.value) {
                    lessons[currentDay.dayOfWeek.value - 1]
                } else {
                    CalendarState(listOf())
                }
            }
        }

        if (lessonsForWeek == null) {
            getLessonsForWeek(updateState)
        } else {
            updateState(lessonsForWeek!!)
        }
    }

    private fun getLessonsForWeek(
        onLessonsRetrievedCallback: ((List<CalendarState>) -> Unit)? = null
    ) {
        lessonListJob?.cancel()

        val universityGroupNumber = UserSession.user?.groupNumber ?: error("group number is null")
        lessonListJob =
            calendarRepository.getLessonsForWeek(universityGroupNumber).onEach { result ->
                isLoading.value = result.isLoading
                isError.value = result.error != null
                result.data?.let { data ->
                    withContext(Dispatchers.Main) {
                        onLessonsRetrievedCallback?.invoke(data)
                        lessonsForWeek = data
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun getLessonsByDay(desiredDay: Int) {
        lessonListJob?.cancel()
        if (currentDayData == null) {
            Log.e(this::class.java.name, "Illegal State: current day is null when get lessons")
        }
        lessonListJob =
            calendarRepository.getLessonsByDay(
                formatter.format(
                    LocalDate.of(currentDayData!!.year, currentDayData!!.month, desiredDay)
                )
            ).onEach { result ->
                isLoading.value = result.isLoading
                isError.value = result.error != null
                result.data?.let { data ->
                    withContext(Dispatchers.Main) {
                        state = data
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun stopJob() {
        monthListJob?.cancel()
        monthListJob = null

        lessonListJob?.cancel()
        lessonListJob = null
    }

    private fun inflateDateForMonth(date: LocalDate?): List<LocalDateWrapper<Boolean>> {
        if (date == null) {
            Log.e(
                this::class.java.name,
                "Error inflating date upon month: date is null"
            )
            return listOf()
        }

        val dayList: MutableList<LocalDateWrapper<Boolean>> = mutableListOf()
        (1..YearMonth.of(date.year, date.month).lengthOfMonth()).forEach {
            dayList.add(
                LocalDateWrapper(LocalDate.of(date.year, date.month, it), it == date.dayOfMonth)
            )
        }

        return dayList
    }

    private fun setCurrentDay(data: List<Month>) {
        if (data.isEmpty()) {
            Log.e(this::class.java.name, "Cannot set current day: empty months list")
            return
        }
        val currentDate = LocalDate.now()
        val date = when {
            currentDate.month.value in data.first().value..data.last().value -> {
                currentDate.dayOfMonth to currentDate.month
            }
            currentDate.month.value < data.first().value -> {
                1 to data.first()
            }
            else -> {
                YearMonth.of(currentDate.year, data.last())
                    .lengthOfMonth() to data.last()
            }
        }
        currentDayData = LocalDate.of(currentDate.year, date.second, date.first)
    }

    private fun setNearestDay(month: Month) {
        val currentDate = LocalDate.now()
        val date = when {
            month.value in requireNotNull(monthListData.value).first().value..requireNotNull(
                monthListData.value
            ).last().value -> {
                // Getting first day of month if month is in list
                1 to month
            }
            month.value < requireNotNull(monthListData.value).first().value -> {
                1 to requireNotNull(monthListData.value).first()
            }
            else -> {
                YearMonth.of(currentDate.year, requireNotNull(monthListData.value).last())
                    .lengthOfMonth() to requireNotNull(monthListData.value).last()
            }
        }
        currentDayData = LocalDate.of(currentDate.year, date.second, date.first)
    }
}