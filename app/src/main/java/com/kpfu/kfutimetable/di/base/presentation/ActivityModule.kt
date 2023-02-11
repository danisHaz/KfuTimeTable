package com.kpfu.kfutimetable.di.base.presentation

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kpfu.kfutimetable.presentation.mainscreen.CalendarViewStateMapper
import com.kpfu.kfutimetable.presentation.mainscreen.providers.CalendarScreen
import com.kpfu.kfutimetable.presentation.mainscreen.providers.CalendarViewModelProvider
import com.kpfu.kfutimetable.presentation.signinscreen.providers.SignInScreen
import com.kpfu.kfutimetable.utils.BASE_URL
import com.kpfu.kfutimetable.utils.routing.RouteManager
import com.kpfu.kfutimetable.utils.routing.Router
import com.kpfu.kfutimetable.utils.routing.ScreenProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    fun provideRouter() = RouteManager.router!!

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    fun provideStandardDateFormatter(): SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    @Provides
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(gsonConverterFactory)
        .build()

    @Provides
    fun provideCalendarScreen(router: Router, screenProvider: ScreenProvider) = CalendarScreen(
        CalendarViewStateMapper(),
        CalendarViewModelProvider(),
        router,
        screenProvider
    )

    @Provides
    fun provideSignInScreen(router: Router, screenProvider: ScreenProvider) =
        SignInScreen(router, screenProvider)
}