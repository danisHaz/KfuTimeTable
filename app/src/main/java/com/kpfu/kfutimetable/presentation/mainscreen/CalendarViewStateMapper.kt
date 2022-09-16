package com.kpfu.kfutimetable.presentation.mainscreen

import com.kpfu.kfutimetable.presentation.base.utils.BaseViewStateMapper
import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarState
import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarViewState

class CalendarViewStateMapper : BaseViewStateMapper<CalendarState, CalendarViewState> {
    override fun mapToViewState(state: CalendarState): CalendarViewState {
        return CalendarViewState(
            state.lessons
        )
    }
}