package com.oscar.hydrosense.home.ui

import android.Manifest
import com.oscar.hydrosense.R
import android.content.Intent
import android.icu.text.DecimalFormat
import android.os.Build
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
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
import kotlin.math.absoluteValue


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
    var waterColor by rememberSaveable { mutableStateOf(0xFFCDFAD5) }
    var phTitle by rememberSaveable { mutableStateOf("Neutro") };
    var phDescription by rememberSaveable { mutableStateOf("Ideal para cuidar plantas o animales") };

    var tempDescription by rememberSaveable { mutableStateOf("Temperatura ideal") };

    var turbidezTitle by rememberSaveable { mutableStateOf("Agua limpia") };

    var estadoSensores: Boolean by rememberSaveable { mutableStateOf(true) }
    var estadoSensoresString: String by rememberSaveable { mutableStateOf("Activado") }
    var buttonColorSensores: Long by rememberSaveable { mutableStateOf(0xFF5AA469) }
    var buttonDescriptionSensores: String by rememberSaveable { mutableStateOf("Apagar") }
    var topStateSenores: Long by rememberSaveable { mutableStateOf(0xFF5AA469) }

    if(estadoSensores != false) {
        buttonColorSensores = 0xFFAF3E3E
        topStateSenores = 0xFF5AA469
        buttonDescriptionSensores = "Apagar";
        estadoSensoresString = "Activado"
    }else{
        buttonColorSensores = 0xFF5AA469;
        topStateSenores = 0xFFAF3E3E
        estadoSensoresString = "Desactivado"
        buttonDescriptionSensores = "Activar"
    }




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

                        phTitle = "Ácido"
                        phDescription = "Dificulta nutrientes en las plantas"
                        estados.add(0)
                    }
                    (it >= 7 && it < 9) -> {
                        phTitle = "Neutro"
                        phDescription = "Ideal para cuidar plantas o animales"
                        estados.add(1)
                    }
                    (it >= 9) -> {

                        estados.add(0)
                        phTitle = "Ácido"
                        phDescription = "Dificulta nutrientes en las plantas" 
                    }
                }
            };
            it.temp.let {
                when {
                    (it < 15) -> {

                        tempDescription = "Temperatura baja"
                        estados.add(0);
                    }
                    (it >= 15 && it < 25) -> {

                        tempDescription = "Temperatura ideal"
                        estados.add(1)
                    }
                    (it >= 25) -> {

                        tempDescription = "Temperatura alta"
                        estados.add(0)
                    }
                }
            }
            it.turbidez.let {
                when {
                    (it <= 5) -> {

                        turbidezTitle = "Agua limpia"
                        estados.add(1)
                    }
                    (it > 5 && it < 25) -> {
                        turbidezTitle = "Agua saturada"
                        estados.add(0)
                    }
                    (it > 25) -> {
                        turbidezTitle = "Agua sucia"
                        estados.add(0)
                    }
                }
            }

        }



        Log.i("OSCAR", "Estados: $estados")

        if(estados.contains(0)){
            waterState = "Mal estado";
            waterColor = 0xFFFF8080;

            if(notificationState != false){
                sensorViewModel.enviarNotificacion("Alerta de calidad del agua", "Se detectaron parámetros fuera del rango seguro. Revisa los sensores y evita el uso del agua hasta corregir el problema");
                notificationState = false;
                timerState = true;
            }

        }else{
            waterState = "Buen estado";
            waterColor = 0xFFCDFAD5;
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
        val permisoNotificaciones = rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS);

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
    Column(modifier.fillMaxSize().padding(15.dp).verticalScroll(scrollState)) {

            Row (Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
                Text(text = "Sensores",
                    style = TextStyle(fontFamily = funnelSans, fontSize = 32.sp, fontWeight = FontWeight.SemiBold))

                Row (verticalAlignment = Alignment.CenterVertically){
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .shadow(4.dp, shape = CircleShape)
                            .background(color = Color(topStateSenores), shape = CircleShape)
                    )

                    Spacer(Modifier.padding(5.dp))
                    Text(text = estadoSensoresString,
                        style = TextStyle(fontFamily = funnelSans, fontSize = 15.sp, fontWeight = FontWeight.SemiBold))

                }

            }




            Spacer(modifier = Modifier.padding(10.dp))

            HorizontalWidget(Modifier, waterState, "Estatus del cuerpo de agua", waterColor);

            Spacer(Modifier.padding(7.dp))
            Row (Modifier.fillMaxWidth().fillMaxHeight(1f)){
                VerticalWidget(Modifier.weight(1f).fillMaxHeight(0.7f),
                    "${data?.ph ?: 0}",
                    "Medicion del ph:",
                    phTitle,
                    phDescription);

                Spacer(Modifier.padding(5.dp))
                Column(Modifier.weight(1f).fillMaxHeight(0.7f), verticalArrangement = Arrangement.SpaceBetween){
                    SquareWidget(Modifier, "${data?.temp ?: 0}", "Temp", "°C", 0xFF5A72A0, tempDescription)
                    Spacer(Modifier.padding(5.dp))
                    SquareWidget(Modifier, "${data?.turbidez ?: 0}", "Turbidez", "UNT", 0xFF1A2130, turbidezTitle)
                }

            }
            Spacer(Modifier.padding(7.dp))

            DirectionWidget(Modifier.height(60.dp));

            Spacer(Modifier.padding(7.dp))

            Button(onClick = {

                if(estadoSensores != true){
                    estadoSensores = true;
                    sensorViewModel.controlSensores(true);
                    Log.i("OSCAR", "Enviando datos")
                }else{
                    estadoSensores = false;
                    sensorViewModel.controlSensores(false);
                    Log.i("OSCAR", "Enviando datos desactivados")

                }

            },
                Modifier.fillMaxWidth()
                    .shadow(8.dp, shape = RoundedCornerShape(10.dp))
                    .height(48.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonColors(
                    containerColor = Color(buttonColorSensores),
                    contentColor = Color.White,
                    disabledContainerColor = Color(buttonColorSensores),
                    disabledContentColor = Color.White
                )) {
                Text(text = buttonDescriptionSensores);
            }


    }
}




@Composable
@Preview(showBackground = true)
fun HomeScreenPreview(){

    Column(Modifier.padding(10.dp).fillMaxSize()) {
/*
        Text(text = "Overview",
            style = TextStyle(fontFamily = funnelSans, fontSize = 32.sp, fontWeight = FontWeight.SemiBold))

        Text(text = "Informacion recogida por tus sensores",
            style = TextStyle(fontFamily = funnelSans, fontSize = 15.sp, fontWeight = FontWeight.Light))

        Spacer(modifier = Modifier.padding(10.dp))

        HorizontalWidget(Modifier, "Buen estado", "Estado general del agua", 0xFF6EC207);

        Spacer(Modifier.padding(7.dp))
        Row (Modifier.fillMaxWidth().fillMaxHeight(1f)){
            VerticalWidget(Modifier.weight(1f).fillMaxHeight(0.7f), "7.0", "Medicion del PH", "Neutro", "Ideal para cuidar plantas o animales");

            Spacer(Modifier.padding(5.dp))
            Column(Modifier.weight(1f).fillMaxHeight(0.7f), verticalArrangement = Arrangement.SpaceBetween){
                SquareWidget(Modifier, "16", "Temp", "° Celsius", 0xFF5A72A0, "adwad")
                SquareWidget(Modifier, "5.0", "Turbidez", "UNT", 0xFF1A2130,"Adwada")
            }

        }
        */

        Spacer(Modifier.padding(7.dp))

        DirectionWidget(Modifier.height(60.dp));
        Spacer(Modifier.padding(7.dp))



    }
}


@Composable
fun HorizontalWidget(modifier: Modifier, data: String, description: String, color: Long){

    Column(modifier = modifier
        .fillMaxWidth()
        .fillMaxHeight(0.3f)
        .shadow(8.dp, shape = RoundedCornerShape(20.dp))
        .background(Color(color))
        .padding(20.dp)
    ) {
        Text("Estado general del agua:",
            style = TextStyle(fontSize = 28.sp, fontFamily = funnelSans, fontWeight = FontWeight.SemiBold),
            modifier = Modifier.width(200.dp));
        Text(data,
            style = TextStyle(fontSize = 28.sp, fontFamily = funnelSans, fontWeight = FontWeight.SemiBold),
            color = Color(0xFF1A2130));

        Text(description,
            style = TextStyle(fontSize = 13.sp, fontFamily = funnelSans, fontWeight = FontWeight.Light));
    }

};

@Composable
fun VerticalWidget(modifier: Modifier, data: String, label: String, titulo: String, descripcion: String){
    Column(modifier = modifier
        .shadow(8.dp, shape = RoundedCornerShape(20.dp))
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
            Text(titulo,
                style = TextStyle(fontSize = 28.sp, fontFamily = funnelSans, fontWeight = FontWeight.SemiBold));

            Text(descripcion,
                style = TextStyle(fontSize = 15.sp, fontFamily = funnelSans, fontWeight = FontWeight.Light),
                textAlign = TextAlign.Center)

            Spacer(Modifier.padding(3.dp))

        }
    }
};

@Composable
fun SquareWidget(modifier: Modifier, data: String, label: String, unidadData: String, color: Long, descripcion: String){

    Column(modifier = Modifier
        .shadow(8.dp, shape = RoundedCornerShape(20.dp))
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

        Column(modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Text("${data}",
                style = TextStyle(fontSize = 55.sp, fontFamily = funnelSans, fontWeight = FontWeight.SemiBold),
                color = Color(0xFFFDFFE2));
            Text("${unidadData}",
                style = TextStyle(fontSize = 14.sp, fontFamily = funnelSans, fontWeight = FontWeight.Light),
                textAlign = TextAlign.Center,
                color = Color(0xFFFDFFE2))
        }

        Text(descripcion,
            style = TextStyle(fontSize = 14.sp, fontFamily = funnelSans, fontWeight = FontWeight.Light),
            textAlign = TextAlign.Center,
            color = Color(0xFFFDFFE2))
    }
}

@Composable
fun DirectionWidget(modifier: Modifier) {
    Row(
        modifier
            .shadow(8.dp, shape = RoundedCornerShape(20.dp))
            .background(Color(0xFFD7D7D7))
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Icon(
            Icons.Outlined.Explore,
            contentDescription = "Ubicacion",
            tint = Color.Black,
            modifier = Modifier.size(33.dp)
        )

        Spacer(Modifier.padding(5.dp))
        Column {

            Text(
                "Blvd. Universidad Tecnológica 225",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = funnelSans,
                    fontWeight = FontWeight.Normal
                ),
            )
            Text(
                "Alias: Sensor UTL",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = funnelSans,
                    fontWeight = FontWeight.Normal
                ),
            )

        }

    }
}



