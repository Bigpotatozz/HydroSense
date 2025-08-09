package com.oscar.hydrosense.home.ui

import com.oscar.hydrosense.R
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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

    var estadoSensores: Boolean by rememberSaveable { mutableStateOf(false) }


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

            HomeScreenPreview();
        }


    }
}




@Composable
@Preview(showBackground = true)
fun HomeScreenPreview(){

    var cardImages: List<Int> = listOf( R.drawable.ph_landscape, R.drawable.temperatura, R.drawable.turbidez);

    val listState = rememberLazyListState();
    val couroutineScope = rememberCoroutineScope();

    val currentItem by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex;
        }
    }



    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Sensores",
            style = TextStyle(fontFamily = funnelSans, fontWeight = FontWeight.Bold, fontSize = 30.sp))

        Row (Modifier.padding(20.dp)){
            CardComponent();
        }

    }

}


@Composable
fun CardComponent(){

    data class CarouselItem(
        val id: Int,
        @DrawableRes val imageResId: Int,
        val contentDescription: String
    );

    val items = remember {
        listOf(
            CarouselItem(0, R.drawable.ph, "cupcake"),
            CarouselItem(1, R.drawable.general_state_img, "donut"),
            CarouselItem(2, R.drawable.temperatura, "eclair"),
        )
    }

}


/*
Box(Modifier.aspectRatio(1.5f/2.5f).clip(RoundedCornerShape(40.dp))){
    Image(painter = painterResource(R.drawable.ph_landscape),
        contentDescription = "PH",
        contentScale = ContentScale.Crop,
        modifier = Modifier.matchParentSize())
}

*/
