package com.kpfu.kfutimetable.di.base.presentation

import com.kpfu.kfutimetable.repository.main.CalendarRepository
import com.kpfu.kfutimetable.repository.main.CalendarRepositoryMock
import com.kpfu.kfutimetable.repository.main.CalendarWebService
import com.kpfu.kfutimetable.repository.signin.SignInRepository
import com.kpfu.kfutimetable.repository.signin.SignInRepositoryImpl
import com.kpfu.kfutimetable.repository.signin.dto.SignInWebService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object MainModule {

    @Provides
    fun provideCalendarRepository(calendarWebService: CalendarWebService): CalendarRepository =
        CalendarRepositoryMock()

    @Provides
    fun provideCalendarService(retrofit: Retrofit): CalendarWebService =
        retrofit.create(CalendarWebService::class.java)

    @Provides
    fun provideSignInRepository(signInWebService: SignInWebService): SignInRepository =
        SignInRepositoryImpl(signInWebService)

    @Provides
    fun provideSignInWebService(retrofit: Retrofit): SignInWebService =
        retrofit.create(SignInWebService::class.java)
}