package com.oscar.hydrosense.login.ui

import android.R
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.style.TextAlign
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
import com.oscar.hydrosense.theme.funnelSans
import com.oscar.hydrosense.ui.PersonalizedTextField
import dagger.hilt.android.internal.Contexts
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.math.log


@Composable
fun LoginScreen(modifier: Modifier, loginViewModel: LoginViewModel, navController: NavController){
    Login(modifier, loginViewModel, navController);
}


@Composable
fun Login(modifier:Modifier, loginViewModel: LoginViewModel, navController: NavController){
    val correo by loginViewModel.correo.observeAsState(initial = "");
    val contrasenia by loginViewModel.contrasenia.observeAsState(initial = "");
    val loginState by loginViewModel.loginState.observeAsState( initial = false);
    val intent by loginViewModel.intento.observeAsState(initial = 0);

    val context = LocalContext.current;


    LaunchedEffect(loginState, intent) {
        if(loginState != false){
            navController.navigate("home")
        }else{
            delay(1000)
            if(intent > 0){
                Toast.makeText(context, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        }
    }


    Column(Modifier.fillMaxSize().padding(31.dp)){
        Spacer(modifier = Modifier.padding(30.dp))

        Column(horizontalAlignment = Alignment.Start){
            Text(text = "Hola!",
                style = TextStyle(fontSize = 32.sp, fontFamily = funnelSans, fontWeight = FontWeight.SemiBold),
                color = Color(0xFF1A2130));
            Text(text = "Bienvenido de vuelta",
                style = TextStyle(fontSize = 15.sp, fontFamily = funnelSans, fontWeight = FontWeight.Light),
                color = Color(0xFF1A2130))
        }
        Spacer(Modifier.padding(30.dp))




        Column() {

            PersonalizedTextField(modifier = Modifier,
                value = correo,
                icono = Icons.Filled.AlternateEmail,
                placeholder = "Correo") {
                loginViewModel.setCorreo(it);
            }

            Spacer(Modifier.padding(10.dp))

            PersonalizedTextField(modifier = Modifier,
                value = contrasenia,
                icono = Icons.Outlined.Visibility,
                placeholder = "Contraseña") {
                loginViewModel.setContrasenia(it);
            }
            Spacer(Modifier.padding(10.dp))

            Button(onClick = {
                loginViewModel.login(correo, contrasenia);

                Log.i("OSCAR", "Intento: ${loginState}")
                if(loginState == false){
                    loginViewModel.setIntento();
                    Log.i("OSCAR", "Intento: ${intent}")
                }
               },
                shape = RoundedCornerShape(22.dp),
                modifier = Modifier.fillMaxWidth().height(47.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF83B4FF),
                    contentColor = Color(0xFF1A2130)
                )) {
                Text(text = "Iniciar sesion", style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Bold))
            }

            Spacer(Modifier.padding(15.dp))

            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {

                HorizontalDivider(Modifier.padding(2.dp).weight(1f),
                    color = Color.Black,
                    thickness = 1.dp)
                Text("or", Modifier.padding(5.dp),
                    style = TextStyle(fontFamily = funnelSans, fontWeight = FontWeight.Bold))
                HorizontalDivider(Modifier.padding(2.dp).weight(1f), color = Color.Black)

            }

            Spacer(Modifier.padding(15.dp))

            Button(onClick = {
                    navController.navigate("register");
            },
                modifier = Modifier.fillMaxWidth().height(47.dp),
                colors = ButtonDefaults.buttonColors( containerColor = Color(0xFF1A2130))) {
                Text("Registrarse",
                    color = Color(0xFFFDFFE2),
                    style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Bold))
            }

            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Olvidaste tu contraseña?",
                    modifier = Modifier.padding(top = 20.dp),
                    style = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = funnelSans),
                    color = Color(0xFF1A2130));


                    Text("Al registrarte, consientes el tratamiento de tus datos conforme a nuestro Aviso de Privacidad.",
                        modifier = Modifier.padding(40.dp),
                        style = TextStyle(fontSize = 10.sp, fontFamily = funnelSans, fontWeight = FontWeight.ExtraLight),
                        textAlign = TextAlign.Center,
                        color = Color(0xFF1A2130))



            }

        }
    }



};

