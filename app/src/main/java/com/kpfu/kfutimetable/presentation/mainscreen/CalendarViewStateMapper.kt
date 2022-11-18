package com.kpfu.kfutimetable.presentation.mainscreen

import com.kpfu.kfutimetable.commonwidgets.DayItemView
import com.kpfu.kfutimetable.commonwidgets.SubjectView
import com.kpfu.kfutimetable.presentation.base.utils.BaseViewStateMapper
import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarState
import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarViewState

class CalendarViewStateMapper : BaseViewStateMapper<CalendarState, CalendarViewState> {
    override fun mapToViewState(state: CalendarState): CalendarViewState {
        return CalendarViewState(
            DayItemView.State("15", "Sat"),
            SubjectView.State(
                "Math",
                SubjectView.State.SubjectType.Seminar,
                "Kremlevskaya, 35",
                "Askhatov",
                "12"
            ),
        )
    }
}