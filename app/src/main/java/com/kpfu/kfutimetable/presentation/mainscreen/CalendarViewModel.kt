package com.kpfu.kfutimetable.presentation.mainscreen

import com.kpfu.kfutimetable.presentation.base.BaseViewModel
import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarState
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