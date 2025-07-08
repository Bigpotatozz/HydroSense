package com.oscar.hydrosense.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import androidx.navigation.NavHost
import com.google.gson.Gson
import com.oscar.hydrosense.login.data.network.dataStore
import com.oscar.hydrosense.login.data.network.response.LoginResponse
import kotlinx.coroutines.flow.first


@Composable
fun HomeScreen(navController: NavController){
    val context = LocalContext.current
    var usuario by remember { mutableStateOf<LoginResponse?>(null); };


    LaunchedEffect(Unit) {
        val prefs = context.dataStore.data.first();
        val json = prefs[stringPreferencesKey("login_response")];
        usuario = Gson().fromJson(json, LoginResponse::class.java)

    }


    Scaffold(
        bottomBar = {Navbar(navController)}
    ) { innerPadding ->
        Column(Modifier.fillMaxSize().padding(innerPadding)) {
            Text("Hola ${usuario?.nombre}")
        }
    }
}

@Composable
fun Navbar(navController: NavController) {

    var selectedItem by rememberSaveable { mutableStateOf(0)}
    val items = listOf("Home", "Profile");
    val selectedIcons = listOf(Icons.Filled.Home, Icons.Filled.AccountCircle)
    val unselectedIcons = listOf(Icons.Filled.Home, Icons.Filled.AccountCircle)

    NavigationBar(modifier = Modifier,
        containerColor = NavigationBarDefaults.containerColor,
        contentColor = MaterialTheme.colorScheme.contentColorFor(NavigationBarDefaults.containerColor)) {

        items.forEachIndexed { index, item ->
            NavigationBarItem(icon = {
                Icon( if (selectedItem == index) selectedIcons[index] else unselectedIcons[index], contentDescription = item)},
            label = { Text(item) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem == index
                    navController.navigate("Account")
                });
        }

    }


}


