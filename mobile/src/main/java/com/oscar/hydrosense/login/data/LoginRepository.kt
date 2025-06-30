package com.oscar.hydrosense.login.data.network

import com.oscar.hydrosense.login.data.network.request.LoginRequest
import com.oscar.hydrosense.login.data.network.response.LoginResponse
import javax.inject.Inject

class LoginRepository @Inject constructor(private val loginService: LoginService){

    suspend fun login(loginRequest: LoginRequest): LoginResponse? {

        return loginService.login(loginRequest);

    }

}