package com.oscar.hydrosense.registro.data.network

import android.util.Log
import com.oscar.hydrosense.registro.data.network.request.RegisterRequest
import com.oscar.hydrosense.registro.data.network.response.RegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterService @Inject constructor(private val registerClient: RegisterClient) {

    suspend fun registrarUsuario(usuario: RegisterRequest): RegisterResponse? {

        return withContext(Dispatchers.IO){
            val response = registerClient.registrarUsuario(usuario);


            if (!response.isSuccessful) {
                Log.e("OSCAR", "Error ${response.code()}: ${response.errorBody()?.string()}")
            }else{
                Log.i("OSCAR", "${response}")
                Log.i("OSCAR", "${response.body()}")
            }
            response.body();

        }

    }

}