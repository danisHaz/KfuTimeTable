package com.kpfu.kfutimetable.utils

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.*

data class User(
    val userId: Int = 0,
    val groupNumber: String
)

object UserSession {
    var user: User? = null
        private set

    private val GROUP_KEY = stringPreferencesKey("GroupNumber")
    private val USER_ID_KEY = intPreferencesKey("UserId")
    private var initializationJob: Job? = null

    /**
     *  Method needed only to restore data from datastore
     */
    fun initialize(context: Context) {
        if (initializationJob != null) {
            Log.e(this::class.java.name, "double initialization of user session")
            initializationJob?.cancel()
        }
        initializationJob = CoroutineScope(Dispatchers.IO).launch {
            this.cancel()
            context.dataStore.data.collect {
                val userId = it[USER_ID_KEY]
                val groupNumber = it[GROUP_KEY]
                user = User(
                    userId ?: -2,
                    groupNumber ?: "09-032"
                )
            }
        }
    }

    /**
     * This method is used when initialization of user may not be completed.
     */
    fun executeOnInitCompletion(onInitializationCompleted: (User?) -> Unit) {
        initializationJob?.invokeOnCompletion { throwable ->
            Log.e("kek", "$user")
            if (throwable != null) {
                Log.e(this::class.java.name, "failure invoke on complete initialization")
                return@invokeOnCompletion
            }

            onInitializationCompleted(user)
        } ?: onInitializationCompleted(user)
    }

    fun update(newUser: User?, context: Context) {
        if (user != null && newUser != null) {
            error("UserSession override is not appropriate")
        }

        CoroutineScope(Dispatchers.IO).launch {
            context.dataStore.edit {
                it[GROUP_KEY] = newUser?.groupNumber ?: ""
                it[USER_ID_KEY] = newUser?.userId ?: -1
            }
        }
        user = newUser
    }
}