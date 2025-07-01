package com.oscar.hydrosense.registro.data.network

import com.oscar.hydrosense.registro.data.network.request.RegisterRequest
import com.oscar.hydrosense.registro.data.network.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterClient {

    @POST("/api/Usuarios/registro")
    suspend fun registrarUsuario(@Body usuario: RegisterRequest): Response<RegisterResponse>;

}