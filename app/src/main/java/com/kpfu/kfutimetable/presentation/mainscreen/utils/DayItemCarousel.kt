package com.kpfu.kfutimetable.presentation.mainscreen.utils

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.kpfu.kfutimetable.commonwidgets.DayItemView
import com.kpfu.kfutimetable.databinding.ViewDayItemBinding

class DayItemCarousel {
}

fun dayItemAdapterDelegate(onClick: (() -> Unit)? = null) =
    adapterDelegateViewBinding<DayItemView.State, DayItemView.State, ViewDayItemBinding>(
        { layoutInflater, root -> ViewDayItemBinding.inflate(layoutInflater, root) }
    ) {

        onClick?.let { binding.root.setOnClickListener { onClick() } }
        bind {
            with(binding) {
                dayOfWeek.text = item.dayOfWeek
                date.text = item.date
            }
        }
    }