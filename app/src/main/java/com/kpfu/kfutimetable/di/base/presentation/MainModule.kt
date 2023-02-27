package com.kpfu.kfutimetable.di.base.presentation

import android.content.Context
import com.kpfu.kfutimetable.R
import com.kpfu.kfutimetable.repository.feedback.FeedbackWebService
import com.kpfu.kfutimetable.repository.main.CalendarRepository
import com.kpfu.kfutimetable.repository.main.CalendarRepositoryImpl
import com.kpfu.kfutimetable.repository.main.CalendarRepositoryMock
import com.kpfu.kfutimetable.repository.main.CalendarWebService
import com.kpfu.kfutimetable.repository.signin.SignInRepository
import com.kpfu.kfutimetable.repository.signin.SignInRepositoryImpl
import com.kpfu.kfutimetable.repository.signin.SignInRepositoryMock
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
        CalendarRepositoryMock()

    @Provides
    fun provideCalendarService(retrofit: Retrofit): CalendarWebService =
        retrofit.create(CalendarWebService::class.java)

    @Provides
    fun provideSignInRepository(signInWebService: SignInWebService): SignInRepository =
        SignInRepositoryMock()

    @Provides
    fun provideSignInWebService(retrofit: Retrofit): SignInWebService =
        retrofit.create(SignInWebService::class.java)

    @Provides
    fun provideFeedbackWebService(retrofit: Retrofit): FeedbackWebService =
        retrofit.create(FeedbackWebService::class.java)

    @Provides
    fun provideDayOfWeekStringArray(@ApplicationContext context: Context): List<String> =
        context.resources.getStringArray(R.array.daysOfWeek).toList()
}