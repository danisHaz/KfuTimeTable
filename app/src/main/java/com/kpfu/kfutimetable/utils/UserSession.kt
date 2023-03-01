package com.kpfu.kfutimetable.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
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
import com.kpfu.kfutimetable.utils.UserSession.PreferenceKeys.USER_PROFILE_PHOTO_KEY
import com.kpfu.kfutimetable.utils.UserSession.PreferenceKeys.USER_SURNAME_KEY
import com.kpfu.kfutimetable.utils.cache.ProfileImageDao
import com.kpfu.kfutimetable.utils.cache.ProfileImageEntity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import java.io.ByteArrayOutputStream

data class User(
    val name: String,
    val surname: String,
    val groupNumber: String,
    val login: String,
    val passwordLength: Int,
    val userProfilePhotoUri: Uri
) {
    companion object {
        val EMPTY = User(
            name = "",
            surname = "",
            groupNumber = "",
            login = "",
            passwordLength = 0,
            userProfilePhotoUri = Uri.EMPTY
        )
    }
}

object UserSession {
    val user: User?
        get() = userData.value
    val profileImage: Bitmap?
        get() = profileImageData.value

    private val userData = MutableLiveData<User?>(null)
    private val profileImageData = MutableLiveData<Bitmap?>(null)
    private var profileImageDao: ProfileImageDao? = null

    private object PreferenceKeys {
        val GROUP_KEY = stringPreferencesKey("GroupNumber")
        val USER_NAME_KEY = stringPreferencesKey("UserName")
        val USER_SURNAME_KEY = stringPreferencesKey("UserSurname")
        val USER_PASSWORD_LENGTH_KEY = intPreferencesKey("UserPasswordLength")
        val USER_LOGIN_KEY = stringPreferencesKey("UserLoginKey")
        val USER_PROFILE_PHOTO_KEY = stringPreferencesKey("UserProfilePhotoKey")
    }

    private var initializationJob: Job? = null

    /**
     *  Method needed only to restore data from datastore
     */
    fun initialize(context: Context, profileImageDao: ProfileImageDao) {
        if (initializationJob != null) {
            Log.e(this::class.java.name, "double initialization of user session")
            initializationJob?.cancel()
        }
        this.profileImageDao = profileImageDao
        initializationJob = CoroutineScope(Dispatchers.IO).launch {
            val prefs = context.dataStore.data.first()
            val name = prefs[USER_NAME_KEY]
            val groupNumber = prefs[GROUP_KEY]
            val surname = prefs[USER_SURNAME_KEY]
            val login = prefs[USER_LOGIN_KEY]
            val passwordLength = prefs[USER_PASSWORD_LENGTH_KEY]
            val userProfilePhotoUri = if (prefs[USER_PROFILE_PHOTO_KEY] != null)
                Uri.parse(prefs[USER_PROFILE_PHOTO_KEY])
            else
                Uri.EMPTY

            if (name != null && groupNumber != null && surname != null && login != null &&
                passwordLength != null
            ) {
                val fetchedUser =
                    User(name, surname, groupNumber, login, passwordLength, userProfilePhotoUri)
                if (fetchedUser != User.EMPTY) {
                    val image = loadProfileImage(profileImageDao)
                    withContext(Dispatchers.Main) {
                        profileImageData.value = image
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
            Log.w(this::class.java.name, "Update user: $user with new user: $newUser")
        }

        CoroutineScope(Dispatchers.IO).launch {
            context.dataStore.edit { prefs ->
                prefs[GROUP_KEY] = newUser?.groupNumber ?: User.EMPTY.groupNumber
                prefs[USER_NAME_KEY] = newUser?.name ?: User.EMPTY.name
                prefs[USER_SURNAME_KEY] = newUser?.surname ?: User.EMPTY.surname
                prefs[USER_LOGIN_KEY] = newUser?.login ?: User.EMPTY.login
                prefs[USER_PASSWORD_LENGTH_KEY] =
                    newUser?.passwordLength ?: User.EMPTY.passwordLength
                prefs[USER_PROFILE_PHOTO_KEY] =
                    newUser?.userProfilePhotoUri.toString()
                        ?: User.EMPTY.userProfilePhotoUri.toString()
            }
        }
        userData.value = newUser
    }

    fun subscribeToUserUpdates(lifecycleOwner: LifecycleOwner, doOnUpdate: (User?) -> Unit) {
        userData.observe(lifecycleOwner, doOnUpdate)
    }

    fun updateProfileImage(newProfileImage: Bitmap?) {
        CoroutineScope(Dispatchers.IO).launch {
            saveProfileImage(profileImageDao, newProfileImage)
        }
        profileImageData.value = newProfileImage
    }

    fun subscribeToProfileImageUpdates(lifecycleOwner: LifecycleOwner, doOnUpdate: (Bitmap?) -> Unit) {
        profileImageData.observe(lifecycleOwner, doOnUpdate)
    }

    private suspend fun loadProfileImage(imageDao: ProfileImageDao): Bitmap? {
        val bytes =
            imageDao.getProfileImage().takeIf { it.isNotEmpty() }?.last()?.image ?: return null
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    private suspend fun saveProfileImage(imageDao: ProfileImageDao?, profileImage: Bitmap?) {
        if (profileImage == null) {
            imageDao?.clearProfileImage()
            return
        }
        val stream = ByteArrayOutputStream()
        profileImage.compress(Bitmap.CompressFormat.PNG, 100, stream)
        imageDao?.updateProfileImage(ProfileImageEntity(1, stream.toByteArray()))
    }
}