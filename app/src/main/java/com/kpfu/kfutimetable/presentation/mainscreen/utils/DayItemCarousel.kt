package com.kpfu.kfutimetable.presentation.mainscreen.utils

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
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
            updateSelectedItem(_selectedItemPosition, value)
            _selectedItemPosition = value
        }
    var onItemClick: (DayItemView.State) -> Unit = {}
    val selectedDayViewState: DayItemView.State?
        get() = _adapter.items?.get(_selectedItemPosition)
    var isRestored = false
    private var needsLazyInit = false
    private var needsScrollToSelectedItem = true

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

    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        return DayItemCarouselSavedState(superState, _selectedItemPosition)
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val data = state as DayItemCarouselSavedState
        super.onRestoreInstanceState(data.superState)

        _selectedItemPosition = data.selectedPosition
        isRestored = true
    }

    override fun render(state: List<DayItemView.State>) {
        _adapter.items = state.toMutableList().apply {
            var ind = 0
            while (ind < size) {
                val s = get(ind)
                if (isRestored) {
                    if (s.isChecked == true) {
                        removeAt(ind)
                        add(ind, s.copy(isChecked = false))
                    }
                    if (ind == _selectedItemPosition) {
                        removeAt(ind)
                        add(ind, s.copy(isChecked = true))
                        onItemClick(s)
                    }
                } else {
                    if (s.isChecked == true) {
                        _selectedItemPosition = ind
                    }
                }
                ind++
            }
        }
        needsScrollToSelectedItem = true
        isRestored = false
        _adapter.notifyDataSetChanged()
    }

    private fun dayItemAdapterDelegate() =
        adapterDelegateViewBinding<DayItemView.State, DayItemView.State, ViewDayItemBinding>(
            { layoutInflater, root ->
                ViewDayItemBinding.inflate(layoutInflater, DayItemView(root.context))
            }
        ) {

            onViewAttachedToWindow {
                if (needsScrollToSelectedItem) {
                    scrollToPosition(_selectedItemPosition)
                    needsScrollToSelectedItem = false
                }
                (binding.root.layoutParams as? MarginLayoutParams)?.apply {
                    marginStart = if (this@adapterDelegateViewBinding.bindingAdapterPosition != 0) {
                        binding.root.context.resources.getDimension(
                            R.dimen.dayItemViewCarousel_marginStart
                        ).roundToInt()
                    } else {
                        binding.root.context.resources.getDimension(
                            R.dimen.dayItemViewCarousel_noMarginStart
                        ).roundToInt()
                    }
                }
            }

            bind {
                (binding.root as DayItemView).let { dayItem ->
                    dayItem.updateOnClick {
                        if (selectedItemPosition != bindingAdapterPosition) {
                            selectedItemPosition = bindingAdapterPosition
                            onItemClick(item)
                        }
                    }
                    dayItem.render(item)
                }
            }
        }

    private fun updateSelectedItem(oldPosition: Int, newPosition: Int) {
        _adapter.items = _adapter.items?.toMutableList()?.apply {
            set(oldPosition, get(oldPosition).copy(isChecked = false))
            set(newPosition, get(newPosition).copy(isChecked = true))
        }
        needsScrollToSelectedItem = true
        _adapter.notifyItemChanged(oldPosition)
        _adapter.notifyItemChanged(newPosition)
    }

    // function required to set listeners and configurations when inflation of view is not
    // controlled
    fun initialize() {
        _adapter = object : AbsDelegationAdapter<List<DayItemView.State>>(
            AdapterDelegatesManager(dayItemAdapterDelegate())
        ) {
            override fun getItemCount() = items?.count() ?: 0
        }.also { adapter = it }
    }

    companion object {
        class DayItemCarouselSavedState : View.BaseSavedState {
            val selectedPosition: Int
            constructor(
                superState: Parcelable?,
                selectedPosition: Int,
            ) : super(superState) {
                this.selectedPosition = selectedPosition
            }

            constructor(savedIn: Parcel) : super(savedIn) {
                selectedPosition = savedIn.readInt()
            }

            override fun writeToParcel(parcel: Parcel, flags: Int) {
                super.writeToParcel(parcel, flags)
                parcel.writeInt(selectedPosition)
            }

            override fun describeContents(): Int {
                return 0
            }

            companion object CREATOR : Parcelable.Creator<DayItemCarouselSavedState> {
                override fun createFromParcel(parcel: Parcel): DayItemCarouselSavedState =
                        DayItemCarouselSavedState(parcel)

                override fun newArray(size: Int): Array<DayItemCarouselSavedState?> =
                    newArray(size)
            }
        }
    }
}