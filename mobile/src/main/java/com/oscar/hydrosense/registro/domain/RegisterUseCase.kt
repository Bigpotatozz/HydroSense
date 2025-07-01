package com.oscar.hydrosense.registro.domain

import com.oscar.hydrosense.registro.data.network.RegisterRepository
import com.oscar.hydrosense.registro.data.network.request.RegisterRequest
import com.oscar.hydrosense.registro.data.network.response.RegisterResponse
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: RegisterRepository) {
    suspend fun invoke(usuario: RegisterRequest): RegisterResponse? {
        var response = repository.registrarUsuario(usuario);
        return response;
    }
}