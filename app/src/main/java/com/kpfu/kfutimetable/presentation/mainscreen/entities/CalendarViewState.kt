package com.kpfu.kfutimetable.presentation.mainscreen.entities

import com.kpfu.kfutimetable.commonwidgets.DayItemView
import com.kpfu.kfutimetable.presentation.base.utils.BaseViewState

data class CalendarViewState(
    val dayItemViewState: DayItemView.State,
) : BaseViewState