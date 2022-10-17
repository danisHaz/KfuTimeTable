package com.kpfu.kfutimetable.di.base.presentation

import com.kpfu.kfutimetable.presentation.base.MainActivity
import com.kpfu.kfutimetable.utils.routing.Router
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Singleton
    @Provides
    fun provideRouter(
        mainActivity: MainActivity
    ) = mainActivity.getGraphRouter()
}