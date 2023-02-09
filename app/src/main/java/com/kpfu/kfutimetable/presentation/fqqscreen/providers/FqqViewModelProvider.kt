package com.kpfu.kfutimetable.presentation.fqqscreen.providers

import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.ViewModelProvider
import com.kpfu.kfutimetable.presentation.base.BaseViewModelProvider
import com.kpfu.kfutimetable.presentation.fqqscreen.FqqViewModel

class FqqViewModelProvider : BaseViewModelProvider<FqqViewModel>{
    override fun createInstance(owner: ViewModelStoreOwner): FqqViewModel {
        return ViewModelProvider(owner).get(FqqViewModel::class.java)
    }
}