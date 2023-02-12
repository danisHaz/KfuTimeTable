package com.kpfu.kfutimetable.di.base.presentation

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kpfu.kfutimetable.repository.main.CalendarRepository
import com.kpfu.kfutimetable.repository.main.CalendarRepositoryMock
import com.kpfu.kfutimetable.repository.main.CalendarWebService
import com.kpfu.kfutimetable.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.format.DateTimeFormatter

@Module
@InstallIn(ViewModelComponent::class)
object MainModule {

    @Provides
    fun provideCalendarRepository(): CalendarRepository = CalendarRepositoryMock()

    @Provides
    fun provideCalendarService(retrofit: Retrofit): CalendarWebService =
        retrofit.create(CalendarWebService::class.java)

     @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    fun provideStandardDateFormatter(): DateTimeFormatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    @Provides
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(gsonConverterFactory)
        .build()
}