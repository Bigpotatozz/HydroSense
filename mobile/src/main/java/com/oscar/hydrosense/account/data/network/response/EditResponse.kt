package com.oscar.hydrosense.account.data.network.response

import com.google.gson.annotations.SerializedName

data class EditResponse (@SerializedName("status") val status: Int,
                         @SerializedName("message") val message: String){
}