package com.oscar.hydrosense.login.ui

import android.content.Context
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
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

    Login(modifier, loginViewModel, navController)
}



@Composable
fun Login(modifier: Modifier, loginViewModel: LoginViewModel, navController: NavController){

    val correo by loginViewModel.correo.observeAsState(initial = "");
    val contrasenia by loginViewModel.contrasenia.observeAsState(initial = "");
    val loginStatus by loginViewModel.loginStatus.observeAsState(initial = false);
    val response by loginViewModel.response.observeAsState();

    val context = LocalContext.current;
    val snackbarHostState = remember { SnackbarHostState() };
    val coroutineScope = rememberCoroutineScope();
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
                loginViewModel.login(correo, contrasenia);
               },
                shape = RoundedCornerShape(22.dp),
                modifier = Modifier.fillMaxWidth()) {
                Text("Iniciar sesion")
            }

            Spacer(Modifier.padding(15.dp))

            Button(onClick = {

                navController.navigate("register");
            }) {
                Text("Registrarse")
            }

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )

        }
    }



};