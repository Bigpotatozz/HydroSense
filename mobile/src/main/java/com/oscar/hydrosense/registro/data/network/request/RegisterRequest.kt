package com.oscar.hydrosense.registro.data.network.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest (val nombre: String,
                            val apellido_paterno: String,
                            val apellido_materno: String,
                            val telefono: String,
                            val edad: Int,
                            val pais: String,
                            val correo: String,
                            val contrasenia: String,
                            ){}