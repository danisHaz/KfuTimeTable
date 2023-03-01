package com.kpfu.kfutimetable.presentation.base.utils

import android.app.Application
import android.util.DisplayMetrics
import androidx.room.Room
import com.kpfu.kfutimetable.utils.UserSession
import com.kpfu.kfutimetable.utils.cache.AppCache
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appDisplayMetrics = resources.displayMetrics
        val roomDB = Room.databaseBuilder(applicationContext, AppCache::class.java, AppCache.NAME)
            .fallbackToDestructiveMigration()
            .build()
        val imageDao = roomDB.getProfileImageDao()
        UserSession.initialize(applicationContext, imageDao)
    }

    companion object {
        var appDisplayMetrics: DisplayMetrics? = null
    }
}