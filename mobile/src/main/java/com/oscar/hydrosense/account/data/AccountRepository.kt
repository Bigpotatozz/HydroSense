package com.oscar.hydrosense.account.data.network

import com.oscar.hydrosense.account.data.network.request.EditRequest
import com.oscar.hydrosense.account.data.network.response.EditResponse
import javax.inject.Inject

class AccountRepository @Inject constructor(private val accountService: AccountService){

    suspend fun editarUsuario(id: Int, usuario: EditRequest, token: String): EditResponse? {
        return accountService.editarUsuario(id, usuario, token);
    }

}