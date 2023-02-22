package com.kpfu.kfutimetable.di.base.presentation

import android.content.Context
import com.kpfu.kfutimetable.repository.main.CalendarRepository
import com.kpfu.kfutimetable.repository.main.CalendarRepositoryImpl
import com.kpfu.kfutimetable.repository.main.CalendarWebService
import com.kpfu.kfutimetable.repository.signin.SignInRepository
import com.kpfu.kfutimetable.repository.signin.SignInRepositoryImpl
import com.kpfu.kfutimetable.repository.signin.dto.SignInWebService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object MainModule {

    @Provides
    fun provideCalendarRepository(
        @ApplicationContext context: Context,
        calendarWebService: CalendarWebService
    ): CalendarRepository =
        CalendarRepositoryImpl(context, calendarWebService)

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