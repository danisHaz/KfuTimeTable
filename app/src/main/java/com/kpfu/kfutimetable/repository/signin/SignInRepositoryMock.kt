package com.kpfu.kfutimetable.repository.signin

import com.kpfu.kfutimetable.repository.signin.dto.UniversityGroupData
import com.kpfu.kfutimetable.repository.signin.dto.UserAuthData
import com.kpfu.kfutimetable.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SignInRepositoryMock : SignInRepository {
    override fun signIn(userAuthData: UserAuthData): Flow<ResultState<UniversityGroupData>> = flow {
        emit(
            ResultState(
                data = UniversityGroupData(
                    groupNumber = "09-032",
                    hasError = false,
                    userName = "Иван",
                    userSurname = "Иванов",
                    login = "IIIvanov",
                    passwordLength = 12,
                )
            )
        )
    }
}