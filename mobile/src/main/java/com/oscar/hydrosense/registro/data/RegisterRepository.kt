package com.oscar.hydrosense.registro.data.network

import com.oscar.hydrosense.registro.data.network.request.RegisterRequest
import com.oscar.hydrosense.registro.data.network.response.RegisterResponse
import javax.inject.Inject

class RegisterRepository @Inject constructor(private val service: RegisterService) {

    suspend fun registrarUsuario(usuario: RegisterRequest): RegisterResponse? {
        return service.registrarUsuario(usuario);
    }

}