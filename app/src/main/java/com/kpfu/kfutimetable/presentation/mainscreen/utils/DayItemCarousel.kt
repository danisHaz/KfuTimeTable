package com.kpfu.kfutimetable.presentation.mainscreen.utils

import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.kpfu.kfutimetable.commonwidgets.DayItemView
import com.kpfu.kfutimetable.databinding.ViewDayItemBinding
import com.kpfu.kfutimetable.utils.dpToPx
import com.kpfu.kfutimetable.utils.pxToDp

class DayItemCarousel {
}

fun dayItemAdapterDelegate(onClick: (() -> Unit)? = null) =
    adapterDelegateViewBinding<DayItemView.State, DayItemView.State, ViewDayItemBinding>(
        { layoutInflater, root ->
            ViewDayItemBinding.inflate(layoutInflater, DayItemView(root.context))
        }
    ) {
        onClick?.let { binding.root.setOnClickListener { onClick() } }

        onViewAttachedToWindow {
            (binding.root.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                if (this@adapterDelegateViewBinding.bindingAdapterPosition != 0)
                    marginStart = 16.dpToPx
            }
        }

        bind {
            with(binding) {
                dayOfWeek.text = item.dayOfWeek
                date.text = item.date
            }
        }
    }