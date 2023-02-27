package com.kpfu.kfutimetable.presentation.feedbackscreen

import androidx.lifecycle.MutableLiveData
import com.kpfu.kfutimetable.di.base.CoroutineDispatcherAnnotations
import com.kpfu.kfutimetable.presentation.base.BaseViewModel
import com.kpfu.kfutimetable.presentation.feedbackscreen.entities.FeedbackState
import com.kpfu.kfutimetable.presentation.feedbackscreen.entities.FeedbackViewState
import com.kpfu.kfutimetable.repository.feedback.FeedbackDto
import com.kpfu.kfutimetable.repository.feedback.FeedbackWebService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.await
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor(
    @CoroutineDispatcherAnnotations.CoroutineDispatcherIO val dispatcher: CoroutineDispatcher,
    val feedbackWebService: FeedbackWebService,
) : BaseViewModel<FeedbackState, FeedbackViewState>(
    initialState = { FeedbackState() },
    viewStateMapper = FeedbackViewStateMapper()
) {

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isError: MutableLiveData<Boolean> = MutableLiveData(false)
    val isSent: MutableLiveData<Boolean> = MutableLiveData(false)
    private var postReportJob: Job? = null

    fun postNewReport(userReport: String) {
        postReportJob?.cancel()
        isLoading.value = false
        isError.value = false

        postReportJob = CoroutineScope(dispatcher).launch {
            isLoading.postValue(true)

            try {
                feedbackWebService.postReport(FeedbackDto(userReport)).await()
            } catch (e: Exception) {
                isError.postValue(true)
                return@launch
            } finally {
                isLoading.postValue(false)
            }

            isSent.postValue(true)
        }
    }
}
