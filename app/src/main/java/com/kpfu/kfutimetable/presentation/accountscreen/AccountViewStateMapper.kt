package com.kpfu.kfutimetable.presentation.accountscreen

import com.kpfu.kfutimetable.presentation.base.utils.BaseViewStateMapper
import com.kpfu.kfutimetable.presentation.accountscreen.entites.AccountState
import com.kpfu.kfutimetable.presentation.accountscreen.entites.AccountViewState

class AccountViewStateMapper : BaseViewStateMapper<AccountState, AccountViewState> {
    override fun mapToViewState(state: AccountState): AccountViewState {
        return AccountViewState()
    }
}