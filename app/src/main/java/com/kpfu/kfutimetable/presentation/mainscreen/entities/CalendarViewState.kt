package com.kpfu.kfutimetable.presentation.mainscreen.entities

import com.kpfu.kfutimetable.commonwidgets.DayItemView
import com.kpfu.kfutimetable.commonwidgets.SubjectView
import com.kpfu.kfutimetable.presentation.base.utils.BaseViewState

data class CalendarViewState(
    val dayItemViewState: DayItemView.State,
    val subjectViewState: List<SubjectView.State>,
    val dayItemCarouselState: List<DayItemView.State>
) : BaseViewState