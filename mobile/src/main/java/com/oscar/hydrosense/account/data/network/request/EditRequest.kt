package com.oscar.hydrosense.account.data.network.request

data class EditRequest (val nombre: String,
                        val apellidoPaterno: String,
                        val apellidoMaterno: String,
                        val edad: Int,
                        val pais: String,
                        val correo: String,
                        val contrasenia: String,
                        val telefono: String) {
}