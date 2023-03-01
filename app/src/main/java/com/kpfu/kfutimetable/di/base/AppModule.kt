package com.kpfu.kfutimetable.di.base

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kpfu.kfutimetable.utils.BASE_URL
import com.kpfu.kfutimetable.utils.cache.AppCache
import com.kpfu.kfutimetable.utils.cache.ProfileImageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.format.DateTimeFormatter
import javax.inject.Qualifier
import javax.inject.Singleton

object CoroutineDispatcherAnnotations {
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class CoroutineDispatcherIO

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class CoroutineDispatcherDefault
}

object DateFormatterAnnotations {
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class DateFormatterReversed

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class DateFormatterStraight
}


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @CoroutineDispatcherAnnotations.CoroutineDispatcherIO
    fun provideCoroutineDispatcherIO() = Dispatchers.IO

    @Provides
    @CoroutineDispatcherAnnotations.CoroutineDispatcherDefault
    fun provideCoroutineDispatcherDefault() = Dispatchers.Default

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    @DateFormatterAnnotations.DateFormatterReversed
    fun provideReversedDateFormatter(): DateTimeFormatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd")

    @Provides
    @DateFormatterAnnotations.DateFormatterStraight
    fun provideStraightDateFormatter(): DateTimeFormatter =
        DateTimeFormatter.ofPattern("dd.MM.yyyy")

    @Provides
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(gsonConverterFactory)
        .build()

    @Singleton
    @Provides
    fun provideAppDatabase(context: Application): AppCache
        = Room.databaseBuilder(context, AppCache::class.java, AppCache.NAME)
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideProfileImageDao(cache: AppCache): ProfileImageDao = cache.getProfileImageDao()
}