package com.kpfu.kfutimetable.commonwidgets.topmenu

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.customview.widget.ViewDragHelper
import com.kpfu.kfutimetable.R
import com.kpfu.kfutimetable.databinding.LayoutTopSlidableMenuBinding

class TopMenuView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private val binding =
        LayoutTopSlidableMenuBinding.inflate(LayoutInflater.from(context), this)
    private var currentDragState = DragState.STATE_COLLAPSED
    private var activePointerId = 0

    private val viewDragHelperCallback = object : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            if (currentDragState == DragState.STATE_DRAGGING) {
                return false
            }

            return true
        }
    }

    init {
        setStyle()
    }

    private fun setStyle() {
        background = ContextCompat.getDrawable(context, R.drawable.top_slideable_menu_background)
    }

    private enum class DragState {
        STATE_DRAGGING,
        STATE_EXPANDED,
        STATE_COLLAPSED,
    }
}