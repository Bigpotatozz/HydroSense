package com.oscar.hydrosense.registro.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue


@Composable
fun RegistroScreen(modifier: Modifier, registerViewModel: RegisterViewModel){

    Registro(registerViewModel);
}


@Composable
fun Registro(registerViewModel: RegisterViewModel){

    val nombre by registerViewModel.nombre.observeAsState(initial = "");
    val correo by registerViewModel.correo.observeAsState(initial = "");
    val contrasenia by registerViewModel.contrasenia.observeAsState(initial = "");
    val telefono by registerViewModel.telefono.observeAsState(initial = "");
    val edad by registerViewModel.edad.observeAsState(initial = "");
    val pais by registerViewModel.pais.observeAsState(initial = "");
    val apellido_paterno by registerViewModel.apellido_paterno.observeAsState(initial = "");
    val apellido_materno by registerViewModel.apellido_materno.observeAsState(initial = "");

    var contrasenia2 by rememberSaveable { mutableStateOf("") };
    var samePassword by rememberSaveable { mutableStateOf(false) };
    var termsAndConditions by rememberSaveable { mutableStateOf(false) }

    Column(Modifier.fillMaxSize().padding(31.dp)) {
        Text("REGISTRO")

        Spacer(Modifier.padding(49.dp))

        OutlinedTextField(value = nombre, onValueChange = {
            registerViewModel.setNombre(it)
        }, placeholder = {Text("Nombre")},
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().height(51.dp)
        )

        OutlinedTextField(value = apellido_paterno, onValueChange = {
            registerViewModel.setApellido_paterno(it)
        }, placeholder = {Text("Apellido paterno")},
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().height(51.dp)
        )

        OutlinedTextField(value = apellido_materno, onValueChange = {
            registerViewModel.setApellido_materno(it)
        }, placeholder = {Text("Apellido paterno")},
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().height(51.dp)
        )

        OutlinedTextField(value = telefono, onValueChange = {
            registerViewModel.setTelefono(it)
        }, placeholder = {Text("Telefono")},
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().height(51.dp)
        )

        OutlinedTextField(value = edad, onValueChange = {
            registerViewModel.setEdad(it)
        }, placeholder = {Text("Edad")},
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().height(51.dp)
        )

        OutlinedTextField(value = pais
            , onValueChange = {
                registerViewModel.setPais(it)
        }, placeholder = {Text("Pais")},
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().height(51.dp)
        )

        OutlinedTextField(value = correo, onValueChange = {
            registerViewModel.setCorreo(it)
        }, placeholder = {Text("Correo")},
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().height(51.dp)
        )

        OutlinedTextField(value = contrasenia, onValueChange = {
            registerViewModel.setContrasenia(it)
        }, placeholder = {Text("Contrasenia")},
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().height(51.dp)
        )

        OutlinedTextField(value = contrasenia2, onValueChange = {

            contrasenia2 = it

            if(contrasenia2 == contrasenia){
                samePassword = true
            }else{
                samePassword = false
            }
        }, placeholder = {Text("Confirmar contrasenia")},
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().height(51.dp)
        )
        Row (verticalAlignment = Alignment.CenterVertically){
            Checkbox(onCheckedChange = {
                termsAndConditions = !termsAndConditions
            },
                checked = termsAndConditions)
            Text("Aceptar terminos y condiciones")
        }



        Button(onClick = {
            if(samePassword == true && termsAndConditions == true){
                Log.i("OSCAR", "${correo} ${contrasenia}")
                registerViewModel.registrarUsuario(nombre,correo,contrasenia,telefono,edad,pais,apellido_paterno,apellido_materno);
            }else{
                Log.i("OSCAR", "contraseña no coincidente")
            }

                         },
            shape = RoundedCornerShape(22.dp),
            modifier = Modifier.fillMaxWidth()) {
            Text("Registrarse")
        }
    }
}