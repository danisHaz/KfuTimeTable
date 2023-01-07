package com.kpfu.kfutimetable.presentation.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.kpfu.kfutimetable.commonwidgets.DayItemView
import com.kpfu.kfutimetable.databinding.FragmentCalendarBinding
import com.kpfu.kfutimetable.presentation.base.BaseFragment
import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarState
import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarViewState
import com.kpfu.kfutimetable.presentation.mainscreen.providers.CalendarViewModelProvider
import com.kpfu.kfutimetable.presentation.mainscreen.utils.MonthCarousel
import com.kpfu.kfutimetable.presentation.mainscreen.utils.dayItemAdapterDelegate
import com.kpfu.kfutimetable.utils.routing.Router
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CalendarFragment @Inject constructor(
    calendarViewStateMapper: CalendarViewStateMapper,
    calendarViewModelProvider: CalendarViewModelProvider,
    private val router: Router,
) : BaseFragment<CalendarState, CalendarViewState, CalendarViewModel>(
    viewStateMapper = calendarViewStateMapper,
    viewModelProvider = calendarViewModelProvider
) {

    private lateinit var binding: FragmentCalendarBinding
    private val monthList = ArrayList<String>().apply {
        add("September")
        add("October")
        add("November")
    }

    private var monthCarousel: MonthCarousel? = null

    private val adapter: AsyncListDifferDelegationAdapter<DayItemView.State> =
        AsyncListDifferDelegationAdapter(
            object : DiffUtil.ItemCallback<DayItemView.State>() {
                override fun areItemsTheSame(
                    oldItem: DayItemView.State,
                    newItem: DayItemView.State
                ): Boolean =
                    oldItem.hashCode() == newItem.hashCode()

                override fun areContentsTheSame(
                    oldItem: DayItemView.State,
                    newItem: DayItemView.State
                ): Boolean = oldItem.date == newItem.date
                        && oldItem.dayOfWeek == newItem.dayOfWeek
            }, AdapterDelegatesManager(
                dayItemAdapterDelegate { }
            )
        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.dayItemCarousel.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//        binding.dayItemCarousel.adapter = adapter
        monthCarousel = MonthCarousel(monthList, binding.monthList)
        setListeners()
    }

    override fun render(currentViewState: CalendarViewState) = with(binding) {
        adapter.items = currentViewState.dayItemCarouselState
        adapter.notifyDataSetChanged()
    }

    private fun setListeners() = with(binding) {
        nextButton.setOnClickListener {
            monthCarousel?.nextMonth()
        }

        prevButton.setOnClickListener {
            monthCarousel?.prevMonth()
        }
    }
}