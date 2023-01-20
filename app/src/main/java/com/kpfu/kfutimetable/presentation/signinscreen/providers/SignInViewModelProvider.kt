package com.kpfu.kfutimetable.presentation.signinscreen.providers

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.kpfu.kfutimetable.presentation.base.BaseViewModelProvider
import com.kpfu.kfutimetable.presentation.signinscreen.SignInViewModel

class SignInViewModelProvider : BaseViewModelProvider<SignInViewModel> {

    override fun createInstance(owner: ViewModelStoreOwner): SignInViewModel {
        return ViewModelProvider(owner).get(SignInViewModel::class.java)
    }
}