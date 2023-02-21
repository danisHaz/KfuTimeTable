package com.kpfu.kfutimetable.presentation.base.utils

import android.app.Application
import android.util.DisplayMetrics
import com.kpfu.kfutimetable.utils.UserSession
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appDisplayMetrics = resources.displayMetrics
        UserSession.initialize(applicationContext)
    }

    companion object {
        var appDisplayMetrics: DisplayMetrics? = null
    }
}