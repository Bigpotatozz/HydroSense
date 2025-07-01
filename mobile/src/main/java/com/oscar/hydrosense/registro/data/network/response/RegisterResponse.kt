package com.oscar.hydrosense.registro.data.network.response

import com.google.gson.annotations.SerializedName

class RegisterResponse (@SerializedName("status") val status: Int,
                        @SerializedName("message") val message: String) {
}