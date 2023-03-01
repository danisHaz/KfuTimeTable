package com.kpfu.kfutimetable.presentation.accountscreen

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmapOrNull
import com.kpfu.kfutimetable.R
import com.kpfu.kfutimetable.databinding.FragmentAccountBinding
import com.kpfu.kfutimetable.presentation.accountscreen.entites.AccountState
import com.kpfu.kfutimetable.presentation.accountscreen.entites.AccountViewState
import com.kpfu.kfutimetable.presentation.accountscreen.providers.AccountViewModelProvider
import com.kpfu.kfutimetable.presentation.base.BaseFragment
import com.kpfu.kfutimetable.utils.UserSession
import com.kpfu.kfutimetable.utils.routing.Router
import com.kpfu.kfutimetable.utils.routing.ScreenProvider
import dagger.hilt.android.AndroidEntryPoint
import io.getstream.avatarview.coil.loadImage
import javax.inject.Inject

@AndroidEntryPoint
class AccountFragment @Inject constructor(
    private val router: Router,
    private val screenProvider: ScreenProvider
) : BaseFragment<AccountState, AccountViewState, AccountViewModel>(
    viewModelProvider = AccountViewModelProvider(),
    viewStateMapper = AccountViewStateMapper()
) {
    private lateinit var binding: FragmentAccountBinding

    private val avatarImageFinderLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            binding.avatarView.loadImage(uri, onError = { request, error ->
                val errorMessage = context?.resources?.getString(R.string.unable_load_image)
                setSnackbar(binding.root, errorMessage, null, null)
            }, onComplete = {
                val newImage = binding.avatarView.drawable.toBitmapOrNull()
                if (uri != null) {
                    UserSession.updateProfileImage(newImage)
                }
            })
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val profileImage = if (UserSession.user != null && UserSession.profileImage != null)
            UserSession.profileImage
        else
            R.drawable.acc
        binding.avatarView.loadImage(profileImage)

        UserSession.user?.let { user ->
            binding.textView1.text = user.login
            val fakePassword = StringBuilder()
            (0 until user.passwordLength).forEach { _ ->
                fakePassword.append('•')
            }
            binding.textView2.text = fakePassword
        }

        setListeners()
    }

    override fun render(currentViewState: AccountViewState) {}

    private fun setListeners() = with(binding) {
        twoicons.faqPageButton.setOnClickListener {
            router.navigate(
                screenProvider.get(ScreenProvider.ScreenType.FqqFragment)
            )
        }
        twoicons.feedbackPageButton.setOnClickListener {
            router.navigate(
                screenProvider.get(ScreenProvider.ScreenType.FeedbackFragment)
            )
        }

        avatarView.setOnClickListener {
            avatarImageFinderLauncher.launch(arrayOf("image/png"))
        }
    }
}