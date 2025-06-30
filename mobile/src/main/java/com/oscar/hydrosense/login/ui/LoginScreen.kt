package com.oscar.hydrosense.login.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun LoginScreen(modifier: Modifier, loginViewModel: LoginViewModel){

    Login(modifier, loginViewModel)
}



@Composable
fun Login(modifier: Modifier, loginViewModel: LoginViewModel){

    val correo by loginViewModel.correo.observeAsState(initial = "");
    val contrasenia by loginViewModel.contrasenia.observeAsState(initial = "");

    Column(Modifier.fillMaxSize().padding(31.dp)){

        Column(horizontalAlignment = Alignment.Start){
            Text(text = "Hola!", style = TextStyle(fontSize = 32.sp));
            Text(text = "Bienvenido de vuelta", style = TextStyle(fontSize = 15.sp))
        }
        Spacer(Modifier.padding(49.dp))

        Column() {
            OutlinedTextField(value = correo, onValueChange = {
                loginViewModel.setCorreo(it);
            }, placeholder = {Text("Correo")},
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth().height(51.dp)
            )

            Spacer(Modifier.padding(10.dp))

            OutlinedTextField(value = contrasenia, onValueChange = {
                loginViewModel.setContrasenia(it);
            }, placeholder = {Text("Contraseña")},
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth().height(51.dp))

            Button(onClick = {
                Log.i("OSCAR", "${correo} ${contrasenia}")
                loginViewModel.login(correo, contrasenia);
                             },
                shape = RoundedCornerShape(22.dp),
                modifier = Modifier.fillMaxWidth()) {
                Text("Iniciar sesion")
            }

            Spacer(Modifier.padding(15.dp))

        }


    }
};