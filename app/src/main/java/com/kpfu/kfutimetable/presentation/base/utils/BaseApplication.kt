package com.kpfu.kfutimetable.presentation.base.utils

import android.app.Application
import android.util.DisplayMetrics
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {
    // here will be stored some dependencies needed along
    // whole app lifecycle

    override fun onCreate() {
        super.onCreate()
        appDisplayMetrics = resources.displayMetrics
    }

    companion object {
        var exitCallback: () -> Unit = {}
        var exitApp: Boolean = false
        set(value) {
            if (value) {
                exitCallback()
            }
            field = value
        }
        var appDisplayMetrics: DisplayMetrics? = null
    }
}