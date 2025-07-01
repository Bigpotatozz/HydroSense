package com.oscar.hydrosense.login.data.network.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(@SerializedName("idUsuario") val idUsuario: Int,
                         @SerializedName("nombre") val nombre: String,
                         @SerializedName("correo") val correo: String,
                         @SerializedName("token") val token: String,
                         @SerializedName("status") val status: Int) {}