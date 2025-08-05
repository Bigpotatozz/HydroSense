package com.oscar.hydrosense.account.data.network

import android.util.Log
import com.oscar.hydrosense.account.data.network.request.EditRequest
import com.oscar.hydrosense.account.data.network.response.EditResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountService @Inject constructor(private val accountClient: AccountClient){

    suspend fun editarUsuario(id: Int, usuario: EditRequest, token: String): EditResponse?{

        return withContext(Dispatchers.IO) {
            val response = accountClient.editarUsuario(id, usuario, token);
            Log.i("OSCAR", "${response}")
            response.body();
        }
    }
}