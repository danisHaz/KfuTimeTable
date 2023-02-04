package com.kpfu.kfutimetable.presentation.accountscreen

import com.kpfu.kfutimetable.presentation.base.BaseViewModel
import com.kpfu.kfutimetable.presentation.accountscreen.entites.AccountState
import com.kpfu.kfutimetable.presentation.accountscreen.entites.AccountViewState
import com.kpfu.kfutimetable.presentation.accountscreen.providers.AccountViewModelProvider
import com.kpfu.kfutimetable.presentation.base.BaseFragment
import com.kpfu.kfutimetable.utils.routing.Router
import com.kpfu.kfutimetable.utils.routing.ScreenProvider
import io.getstream.avatarview.AvatarView
import io.getstream.avatarview.coil.loadImage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AccountFragment @Inject constructor(
    private val router: Router,
    private val screenProvider: ScreenProvider
) : BaseFragment<AccountState, AccountViewState, AccountViewModel>(
    viewModelProvider = { AccountViewModelProvider().createInstance(this@) },
    viewStateMapper = AccountViewStateMapper()
) {


}