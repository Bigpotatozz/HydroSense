package com.oscar.hydrosense.registro.data.network.request

import com.google.gson.annotations.SerializedName


data class RegisterRequest (val nombre: String,
                            val apellidoPaterno: String,
                            val apellidoMaterno: String,
                            val edad: Int,
                            val pais: String,
                            val correo: String,
                            val passwordHash: String,
                            val telefono: String,
                            val fechaRegistro: String = "2025-07-01T17:09:09.814Z",
                            val nivel: String = "1",
                            val token: String = "adaWDWAD"){}