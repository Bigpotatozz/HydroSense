package com.oscar.hydrosense.home.ui

import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.outlined.Explore
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
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

    }

    Home(modifier, navController, viewModel);

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


                    }
                    (it.ph > 7) -> {

                        if(notificationState != false){
                            sensorViewModel.enviarNotificacion("Nivel de ph bajo", "El ph del agua es alto, favor de revisar")
                            notificationState = false;
                            timerState = true;
                        }
                        waterState = "Mal estado"
                        rectangleDescription = "El agua no es optima para su uso"

                    }
                    (it.temp < 15) -> {

                        if(notificationState != false){
                            sensorViewModel.enviarNotificacion("Temperatura baja", "La temperatura del agua es bajo, favor de revisar")
                            notificationState = false;
                            timerState = true;
                        }

                        waterState = "Mal estado"
                        rectangleDescription = "El agua no es optima para su uso"

                    }
                    (it.temp > 25) -> {

                        if(notificationState != false){
                            sensorViewModel.enviarNotificacion("Temperatura baja", "La temperatura del agua es alto, favor de revisar")
                            notificationState = false;
                            timerState = true;
                        }

                        waterState = "Mal estado"
                        rectangleDescription = "El agua no es optima para su uso"

                    }
                    (it.turbidez > 30) -> {
                        if(notificationState != false){
                            sensorViewModel.enviarNotificacion("Turbidez alta", "La turbidez del agua esta alta, favor de revisar")
                            notificationState = false;
                            timerState = true;
                        }

                        waterState = "Mal estado"
                        rectangleDescription = "El agua no es optima para su uso"

                    }
                    else -> {
                        waterState = "Buena"
                        rectangleDescription = "El agua esta limpia y en buen estado"
                        timerState = false;
                        notificationState = true;
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
    Column(modifier = modifier.fillMaxSize().padding(15.dp).verticalScroll(scrollState)) {

        Text(text = "Overview",
            style = TextStyle(fontFamily = funnelSans, fontSize = 32.sp, fontWeight = FontWeight.SemiBold))

        Text(text = "Informacion recogida por tus sensores",
            style = TextStyle(fontFamily = funnelSans, fontSize = 15.sp, fontWeight = FontWeight.Light))

        Spacer(modifier = Modifier.padding(10.dp))

        HorizontalWidget(Modifier, waterState, rectangleDescription);

        Spacer(Modifier.padding(7.dp))
        Row (Modifier.fillMaxWidth().height(340.dp)){
            VerticalWidget(Modifier.weight(1f), "${data?.ph}", "Medicion del PH")

            Spacer(Modifier.padding(5.dp))
            Column(Modifier.weight(1f).fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween){
                SquareWidget(Modifier, "${data?.temp}", "Temp", "° Celsius", 0xFF5A72A0)
                SquareWidget(Modifier, "${data?.turbidez}", "Turbidez", "UNT", 0xFF1A2130)
            }

        }
        Spacer(Modifier.padding(7.dp))

        DirectionWidget(Modifier.height(60.dp));



    }
}

@Composable
fun HorizontalWidget(modifier: Modifier, data: String, description: String){

    Column(modifier = modifier
        .fillMaxWidth()
        .height(200.dp)
        .clip(RoundedCornerShape(20.dp))
        .background(Color(0xFF83B4FF))
        .padding(20.dp)
        ) {
            Text("Estado general del agua:",
                style = TextStyle(fontSize = 28.sp, fontFamily = funnelSans, fontWeight = FontWeight.SemiBold),
                modifier = Modifier.width(200.dp));
            Text(data,
                style = TextStyle(fontSize = 28.sp, fontFamily = funnelSans, fontWeight = FontWeight.SemiBold),
                color = Color(0xFFB3E2A7));

        Text(description,
            style = TextStyle(fontSize = 13.sp, fontFamily = funnelSans, fontWeight = FontWeight.Light));
    }

};

@Composable
fun VerticalWidget(modifier: Modifier, data: String, label: String){
    Column(modifier = modifier
        .clip(RoundedCornerShape(20.dp))
        .background(Color(0xFFFDFFE2))
        .padding(20.dp)
        .fillMaxWidth(),
        verticalArrangement = Arrangement.Center) {
        Text("${label}",
            style = TextStyle(fontSize = 28.sp, fontFamily = funnelSans, fontWeight = FontWeight.SemiBold));
        Spacer(Modifier.padding(18.dp))

        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally){

            Text("${data}",
                style = MaterialTheme.typography.displayLarge.copy(
                    fontFamily = funnelSans,
                    fontWeight = FontWeight.SemiBold
                ));

            Spacer(Modifier.padding(15.dp))
            Text("Neutro",
                style = TextStyle(fontSize = 28.sp, fontFamily = funnelSans, fontWeight = FontWeight.SemiBold));

            Text("Ideal para cuidar plantas o animales",
                style = TextStyle(fontSize = 15.sp, fontFamily = funnelSans, fontWeight = FontWeight.Light),
                textAlign = TextAlign.Center)

            Spacer(Modifier.padding(3.dp))

        }
    }
};

@Composable
fun SquareWidget(modifier: Modifier, data: String, label: String, unidadData: String, color: Long){

    Column(modifier = Modifier
        .clip(RoundedCornerShape(20.dp))
        .background(Color(color))
        .height(165.dp)
        .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text("${label}",
            style = MaterialTheme.typography.titleLarge.copy(
                fontFamily = funnelSans, fontWeight = FontWeight.SemiBold
            ),
            color = Color(0xFFFDFFE2));

        Row(modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            Text("${data}",
                style = TextStyle(fontSize = 55.sp, fontFamily = funnelSans, fontWeight = FontWeight.SemiBold),
                color = Color(0xFFFDFFE2));
            Text("${unidadData}",
                style = TextStyle(fontSize = 14.sp, fontFamily = funnelSans, fontWeight = FontWeight.Light),
                textAlign = TextAlign.Center,
                color = Color(0xFFFDFFE2))
        }

        Text("Temperatura ideal",
            style = TextStyle(fontSize = 14.sp, fontFamily = funnelSans, fontWeight = FontWeight.Light),
            textAlign = TextAlign.Center,
            color = Color(0xFFFDFFE2))
    }
}

@Composable
fun DirectionWidget(modifier: Modifier){
    Row(modifier
        .clip(RoundedCornerShape(10.dp))
        .background(Color(0xFFD7D7D7))
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {

        Icon(Icons.Outlined.Explore,
            contentDescription = "Ubicacion",
            tint = Color.Black,
            modifier = Modifier.size(33.dp))

        Spacer(Modifier.padding(5.dp))
        Text("2 C. Independencia El Jicote, Nayarit",
            style = TextStyle(fontSize = 14.sp, fontFamily = funnelSans, fontWeight = FontWeight.Normal),)
    }
}

