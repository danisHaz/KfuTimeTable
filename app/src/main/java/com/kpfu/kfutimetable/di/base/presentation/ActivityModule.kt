package com.kpfu.kfutimetable.di.base.presentation

import com.kpfu.kfutimetable.presentation.mainscreen.CalendarViewStateMapper
import com.kpfu.kfutimetable.presentation.mainscreen.providers.CalendarScreen
import com.kpfu.kfutimetable.presentation.mainscreen.providers.CalendarViewModelProvider
import com.kpfu.kfutimetable.presentation.signinscreen.providers.SignInScreen
import com.kpfu.kfutimetable.utils.routing.RouteManager
import com.kpfu.kfutimetable.utils.routing.Router
import com.kpfu.kfutimetable.utils.routing.ScreenProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    fun provideRouter() = RouteManager.router!!

    @Provides
    fun provideCalendarScreen() = CalendarScreen(
        CalendarViewStateMapper(),
        CalendarViewModelProvider(),
    )

    @Provides
    fun provideSignInScreen(router: Router, screenProvider: ScreenProvider) =
        SignInScreen(router, screenProvider)
}