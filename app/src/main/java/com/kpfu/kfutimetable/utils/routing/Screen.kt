package com.kpfu.kfutimetable.utils.routing

import androidx.fragment.app.Fragment

interface Screen {

    val className: Class<out Fragment>

    fun createNewInstance(): Fragment
}

interface ScreenFactory {

    fun createScreen(): Screen
}