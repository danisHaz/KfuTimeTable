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
        BaseApplication.appDisplayMetrics = resources.displayMetrics
    }

    companion object {
        var appDisplayMetrics: DisplayMetrics? = null
    }
}