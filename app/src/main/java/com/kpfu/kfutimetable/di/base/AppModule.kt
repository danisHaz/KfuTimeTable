package com.kpfu.kfutimetable.di.base

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kpfu.kfutimetable.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.format.DateTimeFormatter
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CoroutineDispatcherIO

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CoroutineDispatcherDefault

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @CoroutineDispatcherIO
    fun provideCoroutineDispatcherIO() = Dispatchers.IO

    @Provides
    @CoroutineDispatcherDefault
    fun provideCoroutineDispatcherDefault() = Dispatchers.Default

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    fun provideStandardDateFormatter(): DateTimeFormatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd")

    @Provides
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(gsonConverterFactory)
        .build()
}