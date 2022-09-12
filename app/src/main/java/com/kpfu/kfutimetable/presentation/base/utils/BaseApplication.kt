package com.kpfu.kfutimetable.presentation.base.utils

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {
    // here will be stored some dependencies needed along
    // whole app lifecycle
}