package com.kpfu.kfutimetable.presentation.mainscreen

import com.kpfu.kfutimetable.presentation.base.BaseViewModel
import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarState
import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarViewState
import com.kpfu.kfutimetable.presentation.mainscreen.entities.WeekDay
import java.util.*

class CalendarViewModel : BaseViewModel<CalendarState, CalendarViewState>(
    initialState = {
        CalendarState(
            dayOfWeek = WeekDay.Mon,
            lessons = listOf()
        )
    },
    viewStateMapper = CalendarViewStateMapper()
) {

}