package com.kpfu.kfutimetable.repository.signin

import com.kpfu.kfutimetable.repository.signin.dto.AuthResponse
import com.kpfu.kfutimetable.repository.signin.dto.SignInWebService
import com.kpfu.kfutimetable.repository.signin.dto.UserAuthData
import com.kpfu.kfutimetable.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.await
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(
    private val signInWebService: SignInWebService
) : SignInRepository {

    override fun signIn(userAuthData: UserAuthData): Flow<ResultState<AuthResponse>> = flow {
        emit(ResultState(isLoading = true))

        val result: AuthResponse
        try {
            result = signInWebService.signInUser(userAuthData).await()
        } catch (e: HttpException) {
            emit(ResultState(error = e))
            return@flow
        }

        emit(ResultState(data = result))
    }
}