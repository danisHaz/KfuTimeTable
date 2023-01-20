package com.kpfu.kfutimetable.presentation.signinscreen

import com.kpfu.kfutimetable.presentation.base.utils.BaseViewStateMapper
import com.kpfu.kfutimetable.presentation.signinscreen.entities.SignInState
import com.kpfu.kfutimetable.presentation.signinscreen.entities.SignInViewState

class SignInViewStateMapper : BaseViewStateMapper<SignInState, SignInViewState> {
    override fun mapToViewState(state: SignInState): SignInViewState {
        return SignInViewState()
    }
}