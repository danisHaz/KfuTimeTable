package com.kpfu.kfutimetable.presentation.mainscreen.utils

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.hannesdorfmann.adapterdelegates4.AbsDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.kpfu.kfutimetable.R
import com.kpfu.kfutimetable.commonwidgets.BaseView
import com.kpfu.kfutimetable.commonwidgets.DayItemView
import com.kpfu.kfutimetable.databinding.ViewDayItemBinding
import kotlin.math.roundToInt

class DayItemCarousel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr), BaseView<List<DayItemView.State>> {

    private var _selectedItemPosition = 0
    var selectedItemPosition
        get() = _selectedItemPosition
        private set(value) {
            updateSelectedItem(_selectedItemPosition)
            _selectedItemPosition = value
        }
    var onItemClick: (DayItemView.State) -> Unit = {}
    private var needsLazyInit = false

    init {
        (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        overScrollMode = OVER_SCROLL_NEVER

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.DayItemCarousel, defStyleAttr, 0)

        needsLazyInit =
            typedArray.getBoolean(R.styleable.DayItemCarousel_useLazyInitialization, false)

        if (!needsLazyInit) {
            initialize()
        }
        typedArray.recycle()
    }

    private lateinit var _adapter: AbsDelegationAdapter<List<DayItemView.State>>

    override fun render(state: List<DayItemView.State>) {
        _adapter.items = state.toMutableList().apply {
            forEachIndexed { ind, s ->
                if (s.isChecked == true) {
                    _selectedItemPosition = ind
                    onItemClick(s)
                }
            }
        }
        _adapter.notifyDataSetChanged()
    }

    private fun dayItemAdapterDelegate() =
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
                            onItemClick(item)
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

    // function required to set listeners and configurations when inflation of view is not
    // controlled
    fun initialize() {
        _adapter =
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
    }
}