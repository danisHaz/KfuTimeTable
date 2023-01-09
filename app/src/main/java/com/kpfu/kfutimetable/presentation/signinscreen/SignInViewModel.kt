package com.kpfu.kfutimetable.presentation.signinscreen

import com.kpfu.kfutimetable.presentation.base.BaseViewModel
import com.kpfu.kfutimetable.presentation.signinscreen.entities.SignInState
import com.kpfu.kfutimetable.presentation.signinscreen.entities.SignInViewState

class SignInViewModel : BaseViewModel<SignInState, SignInViewState>(
    initialState = { SignInState() },
    viewStateMapper = SignInViewStateMapper()
) {

}