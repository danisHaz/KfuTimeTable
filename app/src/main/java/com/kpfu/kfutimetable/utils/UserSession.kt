package com.kpfu.kfutimetable.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

data class User(
    val userId: Int = 0,
    val groupNumber: String
)

object UserSession {
    var user: User? = null
    private set

    private val GROUP_KEY = stringPreferencesKey("GroupNumber")
    private val USER_ID_KEY = intPreferencesKey("UserId")

    // method needed only to restore data from datastore
    fun initialize(context: Context) {
        context.dataStore.data.map {
            val userId = it[USER_ID_KEY]
            val groupNumber = it[GROUP_KEY]
            if (userId != null && groupNumber != null && userId != -1 && groupNumber != "") {
                user = User(userId, groupNumber)
            }
        }
    }

    fun update(newUser: User?, context: Context) {
        if (user != null && newUser != null) {
            error("UserSession override is not appropriate")
        }

        CoroutineScope(Dispatchers.Default).launch {
            context.dataStore.edit {
                it[GROUP_KEY] = newUser?.groupNumber ?: ""
                it[USER_ID_KEY] = newUser?.userId ?: -1
            }
        }
        user = newUser
    }
}