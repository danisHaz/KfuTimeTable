package com.kpfu.kfutimetable.utils.routing

import androidx.fragment.app.Fragment

interface Screen {
    val className: Class<out Fragment>
}

interface ScreenFactory {

    fun createScreen(): Screen
}