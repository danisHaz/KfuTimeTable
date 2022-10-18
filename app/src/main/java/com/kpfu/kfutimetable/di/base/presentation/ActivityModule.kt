package com.kpfu.kfutimetable.di.base.presentation

import com.kpfu.kfutimetable.presentation.mainscreen.CalendarViewStateMapper
import com.kpfu.kfutimetable.presentation.mainscreen.providers.CalendarScreen
import com.kpfu.kfutimetable.presentation.mainscreen.providers.CalendarViewModelProvider
import com.kpfu.kfutimetable.utils.routing.RouteManager
import com.kpfu.kfutimetable.utils.routing.Router
import com.kpfu.kfutimetable.utils.routing.ScreenProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    fun provideRouter() = RouteManager.router!!

    @Provides
    fun provideCalendarScreen(router: Router) = CalendarScreen(
        CalendarViewStateMapper(),
        CalendarViewModelProvider(),
        router,
    )
}