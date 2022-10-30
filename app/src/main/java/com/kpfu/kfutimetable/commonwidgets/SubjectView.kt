package com.kpfu.kfutimetable.commonwidgets

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.kpfu.kfutimetable.R
import com.kpfu.kfutimetable.databinding.ViewSubjectBinding
import com.kpfu.kfutimetable.utils.fromAttr

class SubjectView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : CardView(context, attrs, defStyleAttr), BaseView<SubjectView.State> {

    private val binding = ViewSubjectBinding.inflate(LayoutInflater.from(context), this)

    init {
        setStyle()
    }

    private fun setStyle() {
        binding.root.background = ContextCompat.getDrawable(
            context,
            R.drawable.view_subject_background
        )
    }

    override fun render(state: State) = with(binding) {
        name.text = state.subject
        address.text = state.address
        teacher.text = state.teacherName
        room.text = state.roomNumber

        root.background = (root.background as GradientDrawable).apply {
            context.fromAttr(state.subjectType.backgroundColorAttr)?.let { setBackgroundColor(it) }
        }
    }

    data class State(
        val subject: String,
        val subjectType: SubjectType,
        val address: String?,
        val teacherName: String,
        val roomNumber: String?,
    ) {
        sealed class SubjectType(val backgroundColorAttr: Int) {
            object Lecture : SubjectType(R.attr.subjectViewBackgroundLectureColor)
            object Seminar : SubjectType(R.attr.subjectViewBackgroundSeminarColor)
        }
    }
}