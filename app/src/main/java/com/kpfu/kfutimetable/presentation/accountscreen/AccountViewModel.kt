package com.kpfu.kfutimetable.presentation.accountscreen

import com.kpfu.kfutimetable.presentation.base.BaseViewModel
import com.kpfu.kfutimetable.presentation.accountscreen.entites.AccountState
import com.kpfu.kfutimetable.presentation.accountscreen.entites.AccountViewState

class AccountViewModel : BaseViewModel<AccountState, AccountViewState>(
    initialState = { AccountState() },
    viewStateMapper = AccountViewStateMapper()
)