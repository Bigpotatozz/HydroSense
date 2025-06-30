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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun RegistroScreen(modifier: Modifier){

}

@Composable
@Preview(showBackground = true)
fun PreviewRegistroScreen(){
    Registro();
}

@Composable
fun Registro(){
    Column(Modifier.fillMaxSize().padding(31.dp)) {
        Text("REGISTRO")

        Spacer(Modifier.padding(49.dp))

        OutlinedTextField(value = " ", onValueChange = {
        }, placeholder = {Text("Nombre")},
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().height(51.dp)
        )

        OutlinedTextField(value = " ", onValueChange = {
        }, placeholder = {Text("Apellido")},
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().height(51.dp)
        )

        OutlinedTextField(value = " ", onValueChange = {
        }, placeholder = {Text("Telefono")},
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().height(51.dp)
        )

        OutlinedTextField(value = " ", onValueChange = {
        }, placeholder = {Text("Edad")},
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().height(51.dp)
        )

        OutlinedTextField(value = " ", onValueChange = {
        }, placeholder = {Text("Pais")},
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().height(51.dp)
        )

        OutlinedTextField(value = " ", onValueChange = {
        }, placeholder = {Text("Correo")},
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().height(51.dp)
        )

        OutlinedTextField(value = " ", onValueChange = {
        }, placeholder = {Text("Contrasenia")},
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().height(51.dp)
        )

        OutlinedTextField(value = " ", onValueChange = {
        }, placeholder = {Text("Confirmar contrasenia")},
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().height(51.dp)
        )
        Row (verticalAlignment = Alignment.CenterVertically){
            Checkbox(onCheckedChange = { Log.i("OSCAR", "adwadawd") }, checked = false)
            Text("Aceptar terminos y condiciones")
        }



        Button(onClick = {
            Log.i("OSCAR", "adwadawd") },
            shape = RoundedCornerShape(22.dp),
            modifier = Modifier.fillMaxWidth()) {
            Text("Registrarse")
        }
    }
}