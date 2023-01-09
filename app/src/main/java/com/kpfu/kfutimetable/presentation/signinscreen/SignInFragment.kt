package com.kpfu.kfutimetable.presentation.signinscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kpfu.kfutimetable.R
import com.kpfu.kfutimetable.presentation.base.BaseFragment
import com.kpfu.kfutimetable.presentation.signinscreen.entities.SignInState
import com.kpfu.kfutimetable.presentation.signinscreen.entities.SignInViewState
import com.kpfu.kfutimetable.presentation.signinscreen.providers.SignInViewModelProvider

class SignInFragment : BaseFragment<SignInState, SignInViewState, SignInViewModel>(
    viewStateMapper = SignInViewStateMapper(),
    viewModelProvider = SignInViewModelProvider()
) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sigin_in, container, false)
    }

    override fun render(currentViewState: SignInViewState) {
        TODO("Not yet implemented")
    }
}