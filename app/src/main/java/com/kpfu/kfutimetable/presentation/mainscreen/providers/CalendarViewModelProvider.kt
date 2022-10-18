package com.kpfu.kfutimetable.presentation.mainscreen.providers

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.kpfu.kfutimetable.presentation.base.BaseViewModelProvider
import com.kpfu.kfutimetable.presentation.mainscreen.CalendarViewModel

class CalendarViewModelProvider : BaseViewModelProvider<CalendarViewModel> {

    override fun createInstance(owner: ViewModelStoreOwner): CalendarViewModel {
        return ViewModelProvider(owner).get(CalendarViewModel::class.java)
    }
}
