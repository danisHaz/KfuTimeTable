package com.kpfu.kfutimetable.di.base.presentation

import com.kpfu.kfutimetable.repository.main.CalendarRepository
import com.kpfu.kfutimetable.repository.mocks.CalendarRepositoryMock
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object MainModule {

    @Provides
    fun provideCalendarRepository(): CalendarRepository = CalendarRepositoryMock()
}