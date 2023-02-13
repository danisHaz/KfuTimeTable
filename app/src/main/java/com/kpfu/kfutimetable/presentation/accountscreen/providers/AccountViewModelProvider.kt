package com.kpfu.kfutimetable.presentation.accountscreen.providers

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.kpfu.kfutimetable.presentation.accountscreen.AccountViewModel
import com.kpfu.kfutimetable.presentation.base.BaseViewModelProvider

class AccountViewModelProvider : BaseViewModelProvider<AccountViewModel> {
    override fun createInstance(owner: ViewModelStoreOwner): AccountViewModel {
        return ViewModelProvider(owner).get(AccountViewModel::class.java)
    }
}