package com.kpfu.kfutimetable.presentation.signinscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kpfu.kfutimetable.databinding.FragmentSiginInBinding
import com.kpfu.kfutimetable.presentation.base.BaseFragment
import com.kpfu.kfutimetable.presentation.signinscreen.entities.SignInState
import com.kpfu.kfutimetable.presentation.signinscreen.entities.SignInViewState
import com.kpfu.kfutimetable.presentation.signinscreen.providers.SignInViewModelProvider
import com.kpfu.kfutimetable.utils.routing.Router
import com.kpfu.kfutimetable.utils.routing.ScreenProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment @Inject constructor(
    private val router: Router,
    private val screenProvider: ScreenProvider
) : BaseFragment<SignInState, SignInViewState, SignInViewModel>(
    viewStateMapper = SignInViewStateMapper(),
    viewModelProvider = SignInViewModelProvider()
) {

    private lateinit var binding: FragmentSiginInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSiginInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setListeners()
    }

    override fun render(currentViewState: SignInViewState) {

    }

    private fun setListeners() {
        binding.loginOrRegister.setOnClickListener {
            router.navigate(
                screenProvider.get(ScreenProvider.ScreenType.CalendarFragment)
            )
        }
    }
}