package com.oscar.hydrosense.home.ui

import com.oscar.hydrosense.R
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.gson.Gson
import com.oscar.hydrosense.helpers.Timer
import com.oscar.hydrosense.login.data.network.dataStore
import com.oscar.hydrosense.login.data.network.response.LoginResponse
import com.oscar.hydrosense.models.NotificacionHelper
import com.oscar.hydrosense.services.SensorService
import com.oscar.hydrosense.theme.funnelSans
import kotlinx.coroutines.flow.first


@Composable
fun HomeScreen(modifier:Modifier, navController: NavController, viewModel: SensorViewModel){
    val context = LocalContext.current
    var usuario by remember { mutableStateOf<LoginResponse?>(null); };



    LaunchedEffect(Unit) {
        val prefs = context.dataStore.data.first();
        val json = prefs[stringPreferencesKey("login_response")];
        usuario = Gson().fromJson(json, LoginResponse::class.java)
        Log.i("OSCAR", "${usuario}")

    }

    Home(modifier.fillMaxSize(), navController, viewModel);

}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Home(modifier: Modifier, navController: NavController, sensorViewModel: SensorViewModel){

    val context = LocalContext.current;
    val data by sensorViewModel.flow.collectAsState(null);
    var notificationState by rememberSaveable {mutableStateOf(true)};
    var timerState by rememberSaveable { mutableStateOf(false) };
    var waterState by rememberSaveable { mutableStateOf("Buena") }
    var rectangleDescription by rememberSaveable { mutableStateOf("El agua esta limpia y en buen estado") };

    var color: Long by rememberSaveable { mutableStateOf(0xFF83B4FF) }

    var filtroState by rememberSaveable { mutableStateOf(false) };
    var filtroString by rememberSaveable { mutableStateOf("apagado") };


    LaunchedEffect(Unit) {
        val intent = Intent(context, SensorService::class.java);
        context.startService(intent);
    }

    LaunchedEffect(data) {

            data?.let {
                when{
                    (it.ph < 6) -> {
                        if(notificationState != false){
                            sensorViewModel.enviarNotificacion("Nivel de ph bajo", "El ph del agua esta bajo, favor de revisar")
                            notificationState = false;
                            timerState = true;
                        }
                        waterState = "Mal estado"
                        rectangleDescription = "El agua no es optima para su uso"
                        color = 0xFFA16D28
                    }
                    (it.ph > 7) -> {
                        if(notificationState != false){
                            sensorViewModel.enviarNotificacion("Nivel de ph bajo", "El ph del agua es alto, favor de revisar")
                            notificationState = false;
                            timerState = true;
                        }
                        waterState = "Mal estado"
                        rectangleDescription = "El agua no es optima para su uso"
                        color = 0xFFA16D28
                    }
                    (it.temp < 15) -> {
                        if(notificationState != false){
                            sensorViewModel.enviarNotificacion("Temperatura baja", "La temperatura del agua es bajo, favor de revisar")
                            notificationState = false;
                            timerState = true;
                        }
                        waterState = "Mal estado"
                        rectangleDescription = "El agua no es optima para su uso"
                        color = 0xFFA16D28
                    }
                    (it.temp > 25) -> {
                        if(notificationState != false){
                            sensorViewModel.enviarNotificacion("Temperatura baja", "La temperatura del agua es alto, favor de revisar")
                            notificationState = false;
                            timerState = true;
                        }
                        waterState = "Mal estado"
                        rectangleDescription = "El agua no es optima para su uso"
                        color = 0xFFA16D28
                    }
                    (it.turbidez > 30) -> {
                        if(notificationState != false){
                            sensorViewModel.enviarNotificacion("Turbidez alta", "La turbidez del agua esta alta, favor de revisar")
                            notificationState = false;
                            timerState = true;
                        }
                        waterState = "Mal estado"
                        rectangleDescription = "El agua no es optima para su uso"
                        color = 0xFFA16D28
                    }
                    else -> {
                        waterState = "Buena"
                        rectangleDescription = "El agua esta limpia y en buen estado"
                        timerState = false;
                        notificationState = true;
                        color = 0xFF83B4FF
                    }
                }
            }

    }

    Log.i("OSCAR", "${data}")

    if(timerState == true) {
        Timer(2000) {
            notificationState = true;
            timerState = false;
        }
    }

    //verifica que la version sea mayor a la oreo
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

        //crea una variable que guarda el permiso para notificaciones de la aplicacion
        val permisoNotificaciones = rememberPermissionState(android.Manifest.permission.POST_NOTIFICATIONS);

        //cuando la aplicacion se ejecute...
        LaunchedEffect(Unit) {
            //verifica que tenga permisos
            if(!permisoNotificaciones.status.isGranted){
                //si no tiene permisos se los pide al usuario
                permisoNotificaciones.launchPermissionRequest();
            }
        }

    };



    val scrollState = rememberScrollState();
    Column(modifier.fillMaxSize().verticalScroll(scrollState)) {
        Column(Modifier.padding(15.dp)) {

            Text(text = "Overview",
                style = TextStyle(fontFamily = funnelSans, fontSize = 32.sp, fontWeight = FontWeight.SemiBold))

            Text(text = "Informacion recogida por tus sensores",
                style = TextStyle(fontFamily = funnelSans, fontSize = 15.sp, fontWeight = FontWeight.Light))


            Spacer(Modifier.padding(5.dp))
            Row {
                Text(text = "Filtro ${filtroString}",
                    style = TextStyle(fontFamily = funnelSans, fontSize = 15.sp, fontWeight = FontWeight.Light))
            }

            Spacer(modifier = Modifier.padding(10.dp))
            InfoComponent(Modifier.height(400.dp), R.drawable.start_image_ph, "${data?.ph}", "ph", "acido");
            Spacer(Modifier.padding(10.dp));
            InfoComponent(Modifier.height(400.dp), R.drawable.startemperature, "${data?.temp}", "°C", "frio");
            Spacer(Modifier.padding(10.dp));
            InfoComponent(Modifier.height(400.dp), R.drawable.starturbidez, "${data?.turbidez}","unt", "limpio");
        }


    }
}



/*
@Composable
@Preview(showBackground = true)
fun HomeScreenPreview(){
    Column(Modifier.padding(15.dp).fillMaxSize()) {
        InfoComponent(Modifier.height(400.dp), R.drawable.start_image_ph, "7.25", "ph", "acido");
    }

}
*/

@Composable
fun InfoComponent(modifier: Modifier = Modifier, imagen: Int, data: String, medida: String, estado: String){

    Column (modifier.shadow(elevation = 2.dp, shape = RoundedCornerShape(15.dp))
        .background(Color.White)){
        Box(Modifier.fillMaxWidth().clip(RoundedCornerShape(15.dp)).weight(1f)){
            Image(painter = painterResource(id = imagen),
                contentDescription = "Imagen de inicio ${medida}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize());
        }

        Row(Modifier.padding(16.dp).background(Color.White).fillMaxWidth()) {
            Column ( modifier = Modifier.weight(1f)){
                Text("Informacion:",
                    style = TextStyle(fontFamily = funnelSans,
                        fontSize = 16.sp, fontWeight = FontWeight.SemiBold));
                Spacer(Modifier.padding(5.dp))
                Row {
                    Box(Modifier.clip(RoundedCornerShape(5.dp)).background(Color(0xFF78C841)).padding(3.dp)){
                        Text("${data}", color = Color.White, style = TextStyle(fontWeight = FontWeight.Bold))
                    }
                    Spacer(Modifier.padding(2.dp))
                    Box(Modifier.clip(RoundedCornerShape(5.dp)).background(Color(0xFF1A2A80)).padding(3.dp)){
                        Text("${medida}", color = Color.White , style = TextStyle(fontWeight = FontWeight.Bold))
                    }
                    Spacer(Modifier.padding(2.dp))
                    Box(Modifier.clip(RoundedCornerShape(5.dp)).background(Color(0xFFD3AF37)).padding(3.dp)){
                        Text("${estado}", color = Color.White , style = TextStyle(fontWeight = FontWeight.Bold))
                    }
                }




            }

            Button(modifier = Modifier
                .height(55.dp)
                .weight(1f),
                shape = RoundedCornerShape(10.dp),

                onClick = {
                }) {
                Row (verticalAlignment = Alignment.CenterVertically,
                    ){

                    Icon(Icons.Filled.KeyboardArrowRight, contentDescription = "Mas informacion")
                    Spacer(Modifier.padding(5.dp))
                    Text("Detalles")
                }

            }
        }
    }

}





