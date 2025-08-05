package com.oscar.hydrosense.account.domain

import com.oscar.hydrosense.account.data.network.AccountRepository
import com.oscar.hydrosense.account.data.network.request.EditRequest
import com.oscar.hydrosense.account.data.network.response.EditResponse
import javax.inject.Inject

class AccountUseCase @Inject constructor(private val repository: AccountRepository) {

    suspend fun invoke(id: Int, usuario: EditRequest, token: String): EditResponse? {
        var response = repository.editarUsuario(id, usuario, token);
        return response;
    }


}