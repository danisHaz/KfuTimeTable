package com.kpfu.kfutimetable.presentation.mainscreen

import com.kpfu.kfutimetable.commonwidgets.DayItemView
import com.kpfu.kfutimetable.commonwidgets.SubjectView
import com.kpfu.kfutimetable.presentation.base.utils.BaseViewStateMapper
import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarState
import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarViewState
import java.util.*

class CalendarViewStateMapper : BaseViewStateMapper<CalendarState, CalendarViewState> {
    override fun mapToViewState(state: CalendarState): CalendarViewState {
        return CalendarViewState(
            DayItemView.State("15", "Sat"),
            SubjectView.State(
                "(Л) Математический анализ",
                SubjectView.State.SubjectType.Seminar,
                "ул. Кремлевская, 35",
                "Асхатов Р.М.",
                "ауд. 216",
                Calendar.getInstance()
            ),
            listOf(
                DayItemView.State("1", "Sat"),
                DayItemView.State("2", "Sat"),
                DayItemView.State("3", "Sat"),
                DayItemView.State("4", "Sat"),
                DayItemView.State("5", "Sat"),
                DayItemView.State("6", "Sat"),
                DayItemView.State("7", "Sat"),
                DayItemView.State("8", "Sat"),
            )
        )
    }
}