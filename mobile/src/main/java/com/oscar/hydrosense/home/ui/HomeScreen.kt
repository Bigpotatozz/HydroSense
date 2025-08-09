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
import androidx.compose.material.icons.filled.WaterDrop
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
import androidx.compose.ui.text.font.FontStyle
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
    var waterState by rememberSaveable { mutableStateOf("No data") }
    var waterColor by rememberSaveable { mutableStateOf(0xFFE43636) }
    var filtroState by rememberSaveable { mutableStateOf(false) };
    var filtroString by rememberSaveable { mutableStateOf("apagado") };
    var color1 by rememberSaveable { mutableStateOf(0xFFE43636) };
    var color2 by rememberSaveable { mutableStateOf(0xFFE43636) };
    var color3 by rememberSaveable { mutableStateOf(0xFFE43636) };


    LaunchedEffect(Unit) {
        val intent = Intent(context, SensorService::class.java);
        context.startService(intent);
    }

    //logica de envio de notificaciones y control de estados
    LaunchedEffect(data) {
        var estados: MutableList<Int> = mutableListOf();

        data?.let {
            it.ph.let {
                when {
                    (it < 7) -> {
                        color1 = 0xFFFFE31A
                        estados.add(0)
                    }
                    (it >= 7 && it < 9) -> {
                        color1 = 0xFF6EC207
                        estados.add(1)
                    }
                    (it >= 9) -> {
                        color1 = 0xFF7BD3EA
                        estados.add(0)
                    }
                }
            };
            it.temp.let {
                when {
                    (it < 15) -> {
                        color2 = 0xFF7BD3EA
                        estados.add(0);
                    }
                    (it >= 15 && it < 25) -> {
                        color2 = 0xFF6EC207
                        estados.add(1)
                    }
                    (it >= 25) -> {
                        color2 = 0xFFFFE31A
                        estados.add(0)
                    }
                }
            }
            it.turbidez.let {
                when {
                    (it <= 5) -> {
                        color3 = 0xFF6EC207
                        estados.add(1)
                    }
                    (it > 5 && it < 25) -> {
                        color3 = 0xFFFFE31A
                        estados.add(0)
                    }
                    (it > 25) -> {
                        color3 = 0xFFE43636
                        estados.add(0)
                    }
                }
            }

        }



        Log.i("OSCAR", "Estados: $estados")

        if(estados.contains(0)){
            waterState = "Mal estado";
            waterColor = 0xFFE43636;

            if(notificationState != false){
                sensorViewModel.enviarNotificacion("Alerta de calidad del agua", "Se detectaron parámetros fuera del rango seguro. Revisa los sensores y evita el uso del agua hasta corregir el problema");
                notificationState = false;
                timerState = true;
            }

        }else{
            waterState = "Buen estado";
            waterColor = 0xFF6EC207;
        }


    }
    Log.i("OSCAR", "${data}")

    if(timerState == true) {
        Timer(60) {
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
            Spacer(Modifier.padding(10.dp))

            WaterStateComponent(estado = waterState, color = waterColor);

            Spacer(modifier = Modifier.padding(10.dp))
            InfoComponent(Modifier.height(400.dp),
                R.drawable.ph, data?.ph,
                "ph",
                "acido",
               color1);
            Spacer(Modifier.padding(10.dp));

            InfoComponent(Modifier.height(400.dp),
                R.drawable.temperatura, data?.temp,
                "°C",
                "frio",
                color2);
            Spacer(Modifier.padding(10.dp));
            InfoComponent(Modifier.height(400.dp),
                R.drawable.turbidez,
                data?.turbidez,
                "unt",
                "limpio",
                color3);


        }


    }
}



/*
@Composable
@Preview(showBackground = true)
fun HomeScreenPreview(){
    Column(Modifier.padding(15.dp).fillMaxSize()) {
        WaterStateComponent(estado = "Buen estado", color = 0xFF6EC207);
        Spacer(Modifier.padding(10.dp))
        InfoComponent(Modifier.height(400.dp), R.drawable.ph, 7.25, "ph", "acido", "Buen estado");
    }

}
*/
@Composable
fun InfoComponent(modifier: Modifier = Modifier,
                  imagen: Int, data: Double?,
                  medida: String,
                  estado: String,
                  color: Long){


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

            Box(Modifier
                .height(55.dp)
                .weight(1f)
                .clip(RoundedCornerShape(15.dp))
                .background(Color(color))
                .padding(15.dp),
                contentAlignment = Alignment.Center

               ){

                Row(Modifier.fillMaxWidth()) {

                    Icon(imageVector = Icons.Filled.WaterDrop,
                        contentDescription = "Icono de agua",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp))
                    Spacer(Modifier.padding(5.dp))
                    Text("${data} ${medida}" ,
                        style = TextStyle(fontFamily = funnelSans, fontWeight = FontWeight.Bold, fontSize = 22.sp),
                        color = Color.White,
                        modifier = Modifier.align(Alignment.CenterVertically))


                }


            }
        }
    }

}

@Composable
fun WaterStateComponent(estado: String, color: Long){

    Row(Modifier
        .fillMaxWidth()
        .shadow(elevation = 2.dp, shape = RoundedCornerShape(15.dp))
        .background(Color(0xFFFFFFDD))
        .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically) {

        Image(painter = painterResource(id = R.drawable.general_state_img),
            contentDescription = "Estado del agua",
            contentScale = ContentScale.Crop,
            modifier = Modifier.weight(1f).size(100.dp))

        Column(Modifier.weight(1f)) {
            Text("Estado general del agua:",
                style = TextStyle(fontFamily = funnelSans, fontWeight = FontWeight.SemiBold, fontSize = 18.sp),
                );

            Spacer(Modifier.padding(5.dp))
            Box(Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color(color))
                .padding(6.dp)
                ){
                Text("${estado}",
                    style = TextStyle(fontFamily = funnelSans, fontWeight = FontWeight.SemiBold, fontSize = 18.sp),
                    color = Color.White)
            }

        }


    }
}





