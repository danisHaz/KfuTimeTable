package com.kpfu.kfutimetable.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import java.time.LocalDate

val BASE_URL = "http://192.168.236.217:5050"

data class LocalDateWrapper<AdditionalParam>(
    val date: LocalDate,
    val param: AdditionalParam
)

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "UserSession")