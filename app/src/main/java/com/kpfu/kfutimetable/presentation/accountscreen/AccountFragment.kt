package com.kpfu.kfutimetable.presentation.accountscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.kpfu.kfutimetable.R
import com.kpfu.kfutimetable.commonwidgets.TopSheetDialog.TopSheetDialog
import com.kpfu.kfutimetable.databinding.FragmentAccountBinding
import com.kpfu.kfutimetable.databinding.FragmentCalendarBinding
import com.kpfu.kfutimetable.presentation.accountscreen.entites.AccountState
import com.kpfu.kfutimetable.presentation.accountscreen.entites.AccountViewState
import com.kpfu.kfutimetable.presentation.accountscreen.providers.AccountViewModelProvider
import com.kpfu.kfutimetable.presentation.base.BaseFragment
import com.kpfu.kfutimetable.utils.routing.Router
import com.kpfu.kfutimetable.utils.routing.ScreenProvider
import dagger.hilt.android.AndroidEntryPoint
import io.getstream.avatarview.AvatarView
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
    private var menuDialog: TopSheetDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        menuDialog = TopSheetDialog(requireContext(), R.style.TopSheet).apply {
            window?.attributes?.windowAnimations = -1
            window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            setContentView(R.layout.layout_top_slidable_menu)
        }

        val avatarView: AvatarView = binding.avatarView.findViewById(R.id.avatarView)
        val drawableRes = R.drawable.acc

        avatarView.loadImage(drawableRes)
    }

    override fun render(currentViewState: AccountViewState) {
    }

    private fun setListeners() = with(binding){
        this.toolbar.menu.setOnClickListener{
            if (menuDialog?.isShowing == true) {
                menuDialog?.hide()

            } else {
                menuDialog?.show()
            }
        }



    }

}