package com.kpfu.kfutimetable.presentation.signinscreen

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kpfu.kfutimetable.presentation.base.BaseViewModel
import com.kpfu.kfutimetable.presentation.signinscreen.entities.SignInState
import com.kpfu.kfutimetable.presentation.signinscreen.entities.SignInViewState
import com.kpfu.kfutimetable.repository.signin.SignInRepository
import com.kpfu.kfutimetable.repository.signin.dto.UserAuthData
import com.kpfu.kfutimetable.utils.User
import com.kpfu.kfutimetable.utils.UserSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInRepository: SignInRepository,
) : BaseViewModel<SignInState, SignInViewState>(
    initialState = { SignInState() },
    viewStateMapper = SignInViewStateMapper()
) {

    private var signInJob: Job? = null

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isError: MutableLiveData<Boolean> = MutableLiveData(false)

    val groupData: MutableLiveData<User> by lazy {
        MutableLiveData(User.EMPTY)
    }

    fun signIn(login: String, password: String) {
        val authData = UserAuthData(login, password)
        signInJob = signInRepository.signIn(authData).onEach {
            isLoading.value = it.isLoading
            isError.value = it.error != null

            it.data?.let { authData ->
                groupData.value = User(
                    groupNumber = authData.groupNumber,
                    name = authData.userName,
                    surname = authData.userSurname,
                    login = authData.login,
                    passwordLength = authData.passwordLength,
                    userProfilePhotoUri = Uri.EMPTY
                )

            }
        }.launchIn(viewModelScope)
    }

    fun stopJob() {
        signInJob?.cancel()
        signInJob = null
    }
}