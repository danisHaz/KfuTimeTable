package com.kpfu.kfutimetable.presentation.mainscreen.providers

import androidx.fragment.app.Fragment
import com.kpfu.kfutimetable.presentation.mainscreen.CalendarFragment
import com.kpfu.kfutimetable.presentation.mainscreen.CalendarViewStateMapper
import com.kpfu.kfutimetable.utils.routing.Screen
import javax.inject.Inject

class CalendarScreen @Inject constructor(
    private val calendarViewStateMapper: CalendarViewStateMapper,
    private val calendarViewModelProvider: CalendarViewModelProvider,
) : Screen {
    override val className: Class<out Fragment>
        get() = CalendarFragment::class.java

    override fun createNewInstance() = CalendarFragment(
        calendarViewStateMapper,
        calendarViewModelProvider,
    )
}