package com.kpfu.kfutimetable.presentation.mainscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kpfu.kfutimetable.presentation.base.BaseViewModel
import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarState
import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarViewState
import com.kpfu.kfutimetable.repository.main.CalendarRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import javax.inject.Inject

class CalendarViewModel @Inject constructor(
    private val calendarRepository: CalendarRepository,
    private val formatter: SimpleDateFormat
) : BaseViewModel<CalendarState, CalendarViewState>(
    initialState = {
        CalendarState(
            date = "2023-02-11 13:24:45",
            lessons = listOf()
        )
    },
    viewStateMapper = CalendarViewStateMapper()
) {

    var monthListJob: Job? = null
    val monthListData: MutableLiveData<List<String>> by lazy {
        MutableLiveData(listOf())
    }
    val isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData(false)
    }
    val isError: MutableLiveData<Boolean> by lazy {
        MutableLiveData(false)
    }

    fun getCurrentMonthsList() {
        monthListJob?.cancel()
        monthListJob = calendarRepository.getCurrentMonths().onEach { result ->
            isLoading.postValue(result.isLoading)
            isError.postValue(result.error != null)
            result.data?.let { data ->
                monthListData.postValue(data.map { formatter.format(it) })
            }
        }.launchIn(viewModelScope)
    }

    fun getLessonsByDay() {

    }

    fun clearResources() {
        monthListJob?.cancel()
        monthListJob = null
    }
}