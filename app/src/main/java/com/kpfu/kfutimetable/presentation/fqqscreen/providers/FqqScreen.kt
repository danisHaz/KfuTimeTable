package com.kpfu.kfutimetable.presentation.fqqscreen.providers

import androidx.fragment.app.Fragment
import com.kpfu.kfutimetable.presentation.fqqscreen.FqqFragment
import com.kpfu.kfutimetable.utils.routing.Screen

class FqqScreen : Screen{
    override val className: Class<out Fragment>
        get() = FqqFragment::class.java

    override fun createNewInstance(): Fragment {
        return FqqFragment()
    }
}