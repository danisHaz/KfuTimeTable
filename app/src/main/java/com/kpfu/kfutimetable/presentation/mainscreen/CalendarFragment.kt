package com.kpfu.kfutimetable.presentation.mainscreen

import com.kpfu.kfutimetable.presentation.base.BaseFragment
import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarState
import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarViewState
import com.kpfu.kfutimetable.presentation.mainscreen.providers.CalendarViewModelProvider
import javax.inject.Inject

class CalendarFragment @Inject constructor(
    calendarViewStateMapper: CalendarViewStateMapper,
    calendarViewModelProvider: CalendarViewModelProvider,
) : BaseFragment<CalendarState, CalendarViewState, CalendarViewModel>(
    initialViewState = {
        CalendarViewState(listOf())
    },
    viewStateMapper = calendarViewStateMapper,
    viewModelProvider = calendarViewModelProvider,
) {
    override fun render(state: CalendarState) {
        TODO("Not yet implemented")
    }


}