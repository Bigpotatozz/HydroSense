package com.oscar.hydrosense.login.data.network

import com.oscar.hydrosense.login.data.network.request.LoginRequest
import com.oscar.hydrosense.login.data.network.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginClient {

    @POST("api/Usuarios/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}