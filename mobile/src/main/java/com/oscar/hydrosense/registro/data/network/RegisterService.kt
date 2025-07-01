package com.oscar.hydrosense.registro.data.network

import com.oscar.hydrosense.registro.data.network.request.RegisterRequest
import com.oscar.hydrosense.registro.data.network.response.RegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterService @Inject constructor(private val registerClient: RegisterClient) {

    suspend fun registrarUsuario(usuario: RegisterRequest): RegisterResponse? {

        return withContext(Dispatchers.IO){
            val response = registerClient.registrarUsuario(usuario);
            response.body();
        }

    }

}