package com.oscar.hydrosense.login.data.network.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(@SerializedName("success") val success: Boolean,
                         @SerializedName("message") val message: String,
                         @SerializedName("data") val data: UsuarioLogin) {}