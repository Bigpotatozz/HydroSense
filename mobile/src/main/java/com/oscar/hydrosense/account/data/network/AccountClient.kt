package com.oscar.hydrosense.account.data.network

import com.oscar.hydrosense.account.data.network.request.EditRequest
import com.oscar.hydrosense.account.data.network.response.EditResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface AccountClient {

    @PUT("/api/Usuarios/editar/{id}")
    suspend fun editarUsuario(@Path("id") id: Int, @Body usuario: EditRequest): Response<EditResponse>;

}