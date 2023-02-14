package com.kpfu.kfutimetable.presentation.signinscreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kpfu.kfutimetable.presentation.base.BaseViewModel
import com.kpfu.kfutimetable.presentation.signinscreen.entities.SignInState
import com.kpfu.kfutimetable.presentation.signinscreen.entities.SignInViewState
import com.kpfu.kfutimetable.repository.signin.SignInRepository
import com.kpfu.kfutimetable.repository.signin.dto.UserAuthData
import dagger.hilt.android.lifecycle.HiltViewModel
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

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isError: MutableLiveData<Boolean> = MutableLiveData(false)

    val groupData: MutableLiveData<String> by lazy {
        MutableLiveData("")
    }

    fun signIn(login: String, password: String) {
        val authData = UserAuthData(login, password)
        signInRepository.signIn(authData).onEach {
            isLoading.value = it.isLoading
            isError.value = it.error == null

            it.data?.let { authData ->
                Log.e(this::class.java.name, authData.toString())
                groupData.value = authData.groupNumber
            }
        }.launchIn(viewModelScope)
    }
}