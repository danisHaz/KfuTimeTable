package com.kpfu.kfutimetable.presentation.mainscreen.entities

import com.kpfu.kfutimetable.commonwidgets.SubjectView
import com.kpfu.kfutimetable.presentation.base.utils.BaseViewState

data class CalendarViewState(
    val daysListState: List<String>,
    val subjectViewState: List<List<SubjectView.State>>,
) : BaseViewState