package com.oscar.hydrosense.home.ui

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavController
import androidx.navigation.NavHost
import com.google.gson.Gson
import com.oscar.hydrosense.login.data.network.dataStore
import com.oscar.hydrosense.login.data.network.response.LoginResponse
import com.oscar.hydrosense.theme.funnelSans
import kotlinx.coroutines.flow.first


@Composable
fun HomeScreen(modifier:Modifier, navController: NavController){
    val context = LocalContext.current
    var usuario by remember { mutableStateOf<LoginResponse?>(null); };


    LaunchedEffect(Unit) {
        val prefs = context.dataStore.data.first();
        val json = prefs[stringPreferencesKey("login_response")];
        usuario = Gson().fromJson(json, LoginResponse::class.java)

    }

    Home();

}



@Composable
@Preview()
fun PreviewHomeScreen(){
    Home();
}

@Composable
fun Home(){
    Column(modifier = Modifier.fillMaxSize().padding(15.dp)) {

        Text(text = "Overview",
            style = TextStyle(fontFamily = funnelSans, fontSize = 32.sp, fontWeight = FontWeight.SemiBold))

        Text(text = "Informacion recogida por tus sensores",
            style = TextStyle(fontFamily = funnelSans, fontSize = 15.sp, fontWeight = FontWeight.Light))

        Spacer(modifier = Modifier.padding(10.dp))

        HorizontalWidget(Modifier, "Buena", "Estado general del agua: ");

        Spacer(Modifier.padding(7.dp))
        Row {
            VerticalWidget(Modifier.weight(1f), "7", "Medicion del PH")

            Column(Modifier.weight(1f)){
                SquareWidget(Modifier, "37", "Temperatura")
                SquareWidget(Modifier, "37", "Temperatura")
            }


        }

    }
}

@Composable
fun HorizontalWidget(modifier: Modifier, data: String, label: String){

    Column(modifier = modifier
        .fillMaxWidth()
        .height(200.dp)
        .clip(RoundedCornerShape(20.dp))
        .background(Color(0xFF83B4FF))
        .padding(20.dp)
        ) {
            Text("${label}",
                style = TextStyle(fontSize = 28.sp, fontFamily = funnelSans, fontWeight = FontWeight.SemiBold),
                modifier = Modifier.width(200.dp));
            Text(data,
                style = TextStyle(fontSize = 28.sp, fontFamily = funnelSans, fontWeight = FontWeight.SemiBold),
                color = Color(0xFFB3E2A7));

        Text("El agua esta limpia y en buen estado",
            style = TextStyle(fontSize = 13.sp, fontFamily = funnelSans, fontWeight = FontWeight.Light));
    }

};

@Composable
fun VerticalWidget(modifier: Modifier, data: String, label: String){
    Column(modifier = Modifier
        .clip(RoundedCornerShape(20.dp))
        .background(Color(0xFFFDFFE2))
        .padding(20.dp),
        verticalArrangement = Arrangement.Center) {
        Text("${label}",
            style = TextStyle(fontSize = 32.sp, fontFamily = funnelSans, fontWeight = FontWeight.SemiBold));
        Spacer(Modifier.padding(18.dp))

        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally){

            Text("${data}",
                style = TextStyle(fontSize = 60.sp, fontFamily = funnelSans, fontWeight = FontWeight.SemiBold));

            Spacer(Modifier.padding(18.dp))
            Text("Neutro",
                style = TextStyle(fontSize = 32.sp, fontFamily = funnelSans, fontWeight = FontWeight.SemiBold));

            Text("Ideal para cuidar plantas o animales",
                style = TextStyle(fontSize = 15.sp, fontFamily = funnelSans, fontWeight = FontWeight.Light),
                textAlign = TextAlign.Center)


        }
    }
};

@Composable
fun SquareWidget(modifier: Modifier, data: String, label: String){

    Column(modifier = Modifier
        .height(174.dp)
        .clip(RoundedCornerShape(20.dp))
        .background(Color(0xFF5A72A0))
        .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text("${label}",
            style = TextStyle(fontSize = 26.sp, fontFamily = funnelSans, fontWeight = FontWeight.SemiBold),
            color = Color(0xFFFDFFE2));

        Row(modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            Text("${data}",
                style = TextStyle(fontSize = 60.sp, fontFamily = funnelSans, fontWeight = FontWeight.SemiBold),
                color = Color(0xFFFDFFE2));
            Text("Grados Celsius",
                style = TextStyle(fontSize = 15.sp, fontFamily = funnelSans, fontWeight = FontWeight.Light),
                textAlign = TextAlign.Center,
                color = Color(0xFFFDFFE2))
        }

        Text("Temperatura ideal",
            style = TextStyle(fontSize = 15.sp, fontFamily = funnelSans, fontWeight = FontWeight.Light),
            textAlign = TextAlign.Center,
            color = Color(0xFFFDFFE2))



    }
}

