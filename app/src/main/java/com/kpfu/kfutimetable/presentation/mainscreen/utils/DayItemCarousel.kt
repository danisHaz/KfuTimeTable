package com.kpfu.kfutimetable.presentation.mainscreen.utils

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.hannesdorfmann.adapterdelegates4.AbsDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.kpfu.kfutimetable.R
import com.kpfu.kfutimetable.commonwidgets.BaseView
import com.kpfu.kfutimetable.commonwidgets.DayItemView
import com.kpfu.kfutimetable.databinding.ViewDayItemBinding
import com.kpfu.kfutimetable.utils.dpToPx
import kotlin.math.roundToInt

class DayItemCarousel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr), BaseView<List<DayItemView.State>> {

    var selectedItemPosition = 0
        private set(value) {
            updateSelectedItem(field)
            field = value
        }

    init {
        (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        overScrollMode = OVER_SCROLL_NEVER
    }

    private val _adapter: AbsDelegationAdapter<List<DayItemView.State>> =
        object : AbsDelegationAdapter<List<DayItemView.State>>(
            AdapterDelegatesManager(
                dayItemAdapterDelegate()
            )
        ) {
            override fun getItemCount(): Int {
                return items?.count() ?: 0
            }
        }.also {
            adapter = it
        }

    override fun render(state: List<DayItemView.State>) {
        _adapter.items = state.toMutableList().apply {
            set(selectedItemPosition, get(selectedItemPosition).copy(isChecked = true))
        }
    }

    private fun dayItemAdapterDelegate(onClick: (() -> Unit)? = null) =
        adapterDelegateViewBinding<DayItemView.State, DayItemView.State, ViewDayItemBinding>(
            { layoutInflater, root ->
                ViewDayItemBinding.inflate(layoutInflater, DayItemView(root.context))
            }
        ) {

            onViewAttachedToWindow {
                (binding.root.layoutParams as? MarginLayoutParams)?.apply {
                    if (this@adapterDelegateViewBinding.bindingAdapterPosition != 0) {
                        marginStart = binding.root.context.resources.getDimension(
                            R.dimen.dayItemViewCarousel_marginStart
                        ).roundToInt()
                    }
                }
            }

            bind {
                (binding.root as DayItemView).let { dayItem ->
                    dayItem.updateOnClick {
                        if (selectedItemPosition != bindingAdapterPosition) {
                            selectedItemPosition = bindingAdapterPosition
                            dayItem.isChecked = !dayItem.isChecked
                            onClick?.invoke()
                        }
                    }
                    dayItem.render(item)
                }
            }
        }

    private fun updateSelectedItem(oldPosition: Int) {
        _adapter.items = _adapter.items?.toMutableList()?.apply {
            set(oldPosition, get(oldPosition).copy(isChecked = false))
        }
        _adapter.notifyItemChanged(oldPosition)
    }
}