package com.kpfu.kfutimetable.repository.signin

import com.kpfu.kfutimetable.repository.signin.dto.UniversityGroupData
import com.kpfu.kfutimetable.repository.signin.dto.UserAuthData
import com.kpfu.kfutimetable.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface SignInRepository {

    fun signIn(userAuthData: UserAuthData): Flow<ResultState<UniversityGroupData>>
}