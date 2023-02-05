package com.kpfu.kfutimetable.presentation.accountscreen

import com.kpfu.kfutimetable.presentation.accountscreen.entites.AccountState
import com.kpfu.kfutimetable.presentation.accountscreen.entites.AccountViewState
import com.kpfu.kfutimetable.presentation.accountscreen.providers.AccountViewModelProvider
import com.kpfu.kfutimetable.presentation.base.BaseFragment
import com.kpfu.kfutimetable.utils.routing.Router
import com.kpfu.kfutimetable.utils.routing.ScreenProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AccountFragment @Inject constructor(
    private val router: Router,
    private val screenProvider: ScreenProvider
) : BaseFragment<AccountState, AccountViewState, AccountViewModel>(
    viewModelProvider = AccountViewModelProvider() ,
    viewStateMapper = AccountViewStateMapper()
) {
    override fun render(currentViewState: AccountViewState) {
        TODO("Not yet implemented")
    }

}