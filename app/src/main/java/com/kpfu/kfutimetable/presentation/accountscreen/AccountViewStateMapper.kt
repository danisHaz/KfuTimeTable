package com.kpfu.kfutimetable.presentation.accountscreen

import com.kpfu.kfutimetable.presentation.accountscreen.entites.AccountState
import com.kpfu.kfutimetable.presentation.accountscreen.entites.AccountViewState
import com.kpfu.kfutimetable.presentation.base.utils.BaseViewStateMapper

class AccountViewStateMapper : BaseViewStateMapper<AccountState, AccountViewState> {
    override fun mapToViewState(state: AccountState): AccountViewState {
        return AccountViewState()
    }
}