package com.oscar.hydrosense.registro.data.network.request

import com.google.gson.annotations.SerializedName


data class RegisterRequest (val nombre: String,
                            val apellidoPaterno: String,
                            val apellidoMaterno: String,
                            val edad: Int,
                            val pais: String,
                            val correo: String,
                            val contrasenia: String,
                            val telefono: String,
                            val nivel: String = "1"){}