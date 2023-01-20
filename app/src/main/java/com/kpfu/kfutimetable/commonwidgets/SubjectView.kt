package com.kpfu.kfutimetable.commonwidgets

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorInt
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.kpfu.kfutimetable.R
import com.kpfu.kfutimetable.databinding.ViewSubjectBinding
import com.kpfu.kfutimetable.utils.changeAlpha
import com.kpfu.kfutimetable.utils.fromAttr
import java.util.*

class SubjectView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : CardView(context, attrs, defStyleAttr), BaseView<SubjectView.State> {

    private val binding = ViewSubjectBinding.inflate(LayoutInflater.from(context), this)

    init {
        setStyle()
        isClickable = true
    }

    override fun render(state: State) = with(binding) {
        name.text = state.subject
        address.text = state.address
        teacher.text = state.teacherName
        room.text = state.roomNumber

        root.background = (root.background as GradientDrawable).apply {
            context.fromAttr(state.subjectType.backgroundColorAttr)?.let {
                mutate()
                color = createColorStateList(it)
            }
        }
    }

    private fun setStyle() = with(binding) {
        root.background = ContextCompat.getDrawable(
            context,
            R.drawable.view_subject_background
        )

        context.fromAttr(R.attr.subjectViewTextColor)?.let {
            listOf(name, room, teacher, address).forEach { view ->
                view.setTextColor(createColorStateList(it))
            }
        }
    }

    private fun createColorStateList(@ColorInt color: Int): ColorStateList {
        val pressedColor = Color.valueOf(color).changeAlpha(OPACITY_COEF)

        return ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_pressed),
                intArrayOf(android.R.attr.state_enabled),
            ),
            intArrayOf(pressedColor.toArgb(), color)
        )
    }

    data class State(
        val subject: String,
        val subjectType: SubjectType,
        val address: String?,
        val teacherName: String,
        val roomNumber: String?,
        val startTime: Calendar,
    ) {
        sealed class SubjectType(val backgroundColorAttr: Int) {
            object Lecture : SubjectType(R.attr.subjectViewBackgroundLectureColor)
            object Seminar : SubjectType(R.attr.subjectViewBackgroundSeminarColor)
        }
    }

    private companion object {
        const val OPACITY_COEF = 0.7f
    }
}