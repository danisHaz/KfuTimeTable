package com.kpfu.kfutimetable.utils

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.kpfu.kfutimetable.utils.UserSession.PreferenceKeys.GROUP_KEY
import com.kpfu.kfutimetable.utils.UserSession.PreferenceKeys.USER_LOGIN_KEY
import com.kpfu.kfutimetable.utils.UserSession.PreferenceKeys.USER_NAME_KEY
import com.kpfu.kfutimetable.utils.UserSession.PreferenceKeys.USER_PASSWORD_LENGTH_KEY
import com.kpfu.kfutimetable.utils.UserSession.PreferenceKeys.USER_SURNAME_KEY
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first

data class User(
    val name: String,
    val surname: String,
    val groupNumber: String,
    val login: String,
    val passwordLength: Int,
) {
    companion object {
        val EMPTY = User(name = "", surname = "", groupNumber = "", login = "", passwordLength = 0)
    }
}

object UserSession {
    val user: User?
        get() = userData.value

    private val userData = MutableLiveData<User?>(null)

    private object PreferenceKeys {
        val GROUP_KEY = stringPreferencesKey("GroupNumber")
        val USER_NAME_KEY = stringPreferencesKey("UserName")
        val USER_SURNAME_KEY = stringPreferencesKey("UserSurname")
        val USER_PASSWORD_LENGTH_KEY = intPreferencesKey("UserPasswordLength")
        val USER_LOGIN_KEY = stringPreferencesKey("UserLoginKey")
    }

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
            val login = prefs[USER_LOGIN_KEY]
            val passwordLength = prefs[USER_PASSWORD_LENGTH_KEY]
            if (name != null && groupNumber != null && surname != null && login != null &&
                passwordLength != null
            ) {
                val fetchedUser = User(name, surname, groupNumber, login, passwordLength)
                if (fetchedUser != User.EMPTY) {
                    withContext(Dispatchers.Main) {
                        userData.value = fetchedUser
                    }
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
            context.dataStore.edit { prefs ->
                prefs[GROUP_KEY] = newUser?.groupNumber ?: User.EMPTY.groupNumber
                prefs[USER_NAME_KEY] = newUser?.name ?: User.EMPTY.name
                prefs[USER_SURNAME_KEY] = newUser?.surname ?: User.EMPTY.surname
                prefs[USER_LOGIN_KEY] = newUser?.login ?: User.EMPTY.login
                prefs[USER_PASSWORD_LENGTH_KEY] = newUser?.passwordLength ?: User.EMPTY.passwordLength
            }
        }
        userData.value = newUser
    }

    fun subscribeToUserUpdates(lifecycleOwner: LifecycleOwner, doOnUpdate: (User?) -> Unit) {
        userData.observe(lifecycleOwner, doOnUpdate)
    }
}