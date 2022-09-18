package com.kpfu.kfutimetable.presentation.mainscreen

import com.kpfu.kfutimetable.presentation.base.BaseViewModel
import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarState
import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarViewState
import java.util.*

class CalendarViewModel : BaseViewModel<CalendarState>(
    initialState = {
        CalendarState(
            date = Date(),
            lessons = listOf()
        )
    }
) {

}