package com.kpfu.kfutimetable.utils

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first

data class User(
    val name: String,
    val surname: String,
    val groupNumber: String
) {
    companion object {
        val EMPTY = User(name="", surname="", groupNumber="")
    }
}

object UserSession {
    val user: User?
        get() = userData.value

    private val userData = MutableLiveData<User?>(null)

    private val GROUP_KEY = stringPreferencesKey("GroupNumber")
    private val USER_NAME_KEY = stringPreferencesKey("UserName")
    private val USER_SURNAME_KEY = stringPreferencesKey("UserSurname")
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
            val prefs = context.dataStore.data.first()
            val name = prefs[USER_NAME_KEY]
            val groupNumber = prefs[GROUP_KEY]
            val surname = prefs[USER_SURNAME_KEY]
            if (name != null && groupNumber != null && surname != null
                && name != "" && groupNumber != "" && surname != "") {
                withContext(Dispatchers.Main) {
                    userData.value = User(name, surname, groupNumber)
                }
            }
        }
    }

    /**
     * This method is used when initialization of user may not be completed.
     */
    fun executeOnInitCompletion(onInitializationCompleted: (User?) -> Unit) {
        initializationJob?.invokeOnCompletion { throwable ->
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
                it[USER_NAME_KEY] = newUser?.name ?: ""
                it[USER_SURNAME_KEY] = newUser?.surname ?: ""
            }
        }
        userData.value = newUser
    }

    fun subscribeToUserUpdates(lifecycleOwner: LifecycleOwner, doOnUpdate: (User?) -> Unit) {
        userData.observe(lifecycleOwner, doOnUpdate)
    }
}