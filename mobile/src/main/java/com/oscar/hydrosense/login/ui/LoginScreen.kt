package com.oscar.hydrosense.login.ui

import android.R
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavController
import com.google.gson.Gson
import com.oscar.hydrosense.login.data.network.response.LoginResponse
import dagger.hilt.android.internal.Contexts
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "usuario");

@Composable
fun LoginScreen(modifier: Modifier, loginViewModel: LoginViewModel, navController: NavController){

    Login()
}


@Preview(showBackground = true)
@Composable
fun PreviewLogin(){



    Login();
}

@Composable
fun Login(/*modifier:Modifier, loginViewModel: LoginViewModel, navController: NavController*/){

    /*val correo by loginViewModel.correo.observeAsState(initial = "");
    val contrasenia by loginViewModel.contrasenia.observeAsState(initial = "");
    val loginStatus by loginViewModel.loginStatus.observeAsState(initial = false);
    val response by loginViewModel.response.observeAsState();*/

    val context = LocalContext.current;
    val snackbarHostState = remember { SnackbarHostState() };
    val coroutineScope = rememberCoroutineScope();

    /*
    LaunchedEffect(loginStatus) {
        if(loginStatus){
            Log.i("OSCAR", "todo bien");
            Log.i("OSCAR", "${response?.nombre}");
            val json = Gson().toJson(response);
            val key = stringPreferencesKey("login_response");
            context.dataStore.edit { prefs -> prefs[key] = json };

            navController.navigate("home")

        }else{
            Log.i("OSCAR", "todo mal")
            coroutineScope.launch {
                snackbarHostState.showSnackbar("Error al registrar")
            }
        }
    }
     */

    Column(Modifier.fillMaxSize().padding(31.dp)){

        Column(horizontalAlignment = Alignment.Start){
            Text(text = "Hola!", style = TextStyle(fontSize = 32.sp));
            Text(text = "Bienvenido de vuelta", style = TextStyle(fontSize = 15.sp))
        }
        Spacer(Modifier.padding(49.dp))




        Column() {

            PersonalizedTextField(value = ""/*correo*/,
                icono = Icons.Filled.AlternateEmail,
                placeholder = "Correo") {
                //loginViewModel.setCorreo(it);
            }

            Spacer(Modifier.padding(10.dp))

            PersonalizedTextField(value = ""/*contrasenia*/,
                icono = Icons.Outlined.Visibility,
                placeholder = "Contraseña") {
                //loginViewModel.setContrasenia(it);
            }
            Spacer(Modifier.padding(10.dp))

            Button(onClick = {
                //loginViewModel.login(correo, contrasenia);
               },
                shape = RoundedCornerShape(22.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF83B4FF),
                    contentColor = Color(0xFF1A2130)
                )) {
                Text(text = "Iniciar sesion", style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Bold))
            }

            Spacer(Modifier.padding(15.dp))

            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                HorizontalDivider(Modifier.padding(2.dp).weight(1f), color = Color.Black)
                Text("or", Modifier.padding(5.dp))
                HorizontalDivider(Modifier.padding(2.dp).weight(1f), color = Color.Black)
            }

            Spacer(Modifier.padding(15.dp))

            Button(onClick = {

                //navController.navigate("register");
                /*
                if(loginStatus == false){
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Usuario o contraseña incorrectos");
                    }
                }

                */
            },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors( containerColor = Color(0xFF1A2130))) {
                Text("Registrarse", color = Color(0xFFFDFFE2))
            }

            Text("Olvidaste tu contraseña?");

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )

        }
    }



};


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalizedTextField(value: String, icono: ImageVector, placeholder: String, onValueChange: (String) -> Unit){

    OutlinedTextField(value = value, onValueChange = onValueChange , placeholder = {
        Row(verticalAlignment = Alignment.CenterVertically){

            Text(text = placeholder,
                style = TextStyle( fontSize = 15.sp))
        } },
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth().height(51.dp),
        leadingIcon = {
            Icon(imageVector = icono,
            contentDescription = placeholder)},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFF1A2130),
            unfocusedBorderColor = Color.Black,
            errorBorderColor = Color.Red
        )
    )
}