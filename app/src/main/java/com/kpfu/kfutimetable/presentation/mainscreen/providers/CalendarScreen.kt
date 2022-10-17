package com.kpfu.kfutimetable.presentation.mainscreen.providers

import androidx.fragment.app.Fragment
import com.kpfu.kfutimetable.presentation.mainscreen.CalendarFragment
import com.kpfu.kfutimetable.utils.routing.Screen

class CalendarScreen : Screen {
    override val className: Class<out Fragment>
        get() = CalendarFragment::class.java

    override fun createNewInstance(): CalendarFragment {

    }
}