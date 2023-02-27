package com.kpfu.kfutimetable.presentation.signinscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kpfu.kfutimetable.databinding.FragmentSiginInBinding
import com.kpfu.kfutimetable.presentation.base.BaseFragment
import com.kpfu.kfutimetable.presentation.base.MainActivity
import com.kpfu.kfutimetable.presentation.signinscreen.entities.SignInState
import com.kpfu.kfutimetable.presentation.signinscreen.entities.SignInViewState
import com.kpfu.kfutimetable.presentation.signinscreen.providers.SignInViewModelProvider
import com.kpfu.kfutimetable.utils.User
import com.kpfu.kfutimetable.utils.UserSession
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
        (requireActivity() as MainActivity).disableToolbar()
        setListeners()
        setObservers()
    }

    override fun render(currentViewState: SignInViewState) {}

    override fun onDestroy() {
        super.onDestroy()
        viewModel.stopJob()
    }

    private fun setListeners() = with(binding) {
        loginOrRegister.setOnClickListener {
            viewModel.signIn(
                binding.username.text.toString(),
                binding.password.text.toString(),
            )
        }
    }

    private fun setObservers() {
        viewModel.groupData.observe(viewLifecycleOwner) {
            if (it.groupNumber == "")
                return@observe
            UserSession.update(it, requireContext())
            onSuccessfulLogin()
        }

        viewModel.isError.observe(viewLifecycleOwner) {
            if (it) {
                setSnackbar(
                    binding.root,
                    "Неправильно введены данные",
                    action = null,
                    toPerform = null
                )
            }
        }
    }

    private fun onSuccessfulLogin() {
        (requireActivity() as MainActivity).enableToolbar()
        router.navigate(
            screenProvider.get(ScreenProvider.ScreenType.CalendarFragment),
            addToBackStack = false,
            executePendingTransactions = true
        )
    }
}