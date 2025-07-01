package com.oscar.hydrosense.login.data.network.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(@SerializedName("status") val status: Int,
                         @SerializedName("token") val token: String ) {}