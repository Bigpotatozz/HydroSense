package com.oscar.hydrosense.login.domain

import com.oscar.hydrosense.login.data.network.LoginRepository
import com.oscar.hydrosense.login.data.network.request.LoginRequest
import com.oscar.hydrosense.login.data.network.response.LoginResponse
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {

    suspend operator fun invoke(loginRequest: LoginRequest): LoginResponse? {

        var loginResponse = loginRepository.login(loginRequest);
        return loginResponse;
    }

}