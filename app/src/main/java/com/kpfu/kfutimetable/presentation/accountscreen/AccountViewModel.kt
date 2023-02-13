package com.kpfu.kfutimetable.presentation.accountscreen

import com.kpfu.kfutimetable.presentation.accountscreen.entites.AccountState
import com.kpfu.kfutimetable.presentation.accountscreen.entites.AccountViewState
import com.kpfu.kfutimetable.presentation.base.BaseViewModel

class AccountViewModel : BaseViewModel<AccountState, AccountViewState>(
    initialState = { AccountState() },
    viewStateMapper = AccountViewStateMapper()
)