package com.kpfu.kfutimetable.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import java.time.LocalDate
import java.time.Month

val BASE_URL = "http://192.168.79.241:5050/"

data class LocalDateWrapper<AdditionalParam>(
    val date: LocalDate,
    val param: AdditionalParam
)

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "UserSession")

val term1 = listOf(Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER)
val term2 = listOf(Month.FEBRUARY, Month.MARCH, Month.APRIL, Month.MAY)