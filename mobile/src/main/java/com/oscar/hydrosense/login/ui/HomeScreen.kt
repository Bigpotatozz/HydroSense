package com.oscar.hydrosense.login.ui

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavController
import com.google.android.gms.common.internal.Objects
import com.google.gson.Gson
import com.oscar.hydrosense.login.data.network.response.LoginResponse
import kotlinx.coroutines.flow.first


@Composable
fun HomeScreen(navController: NavController){
    val context = LocalContext.current
    var usuario by rememberSaveable { mutableStateOf<LoginResponse?>(null); };


    LaunchedEffect(Unit) {
        val prefs = context.dataStore.data.first();
        val json = prefs[stringPreferencesKey("login_response")];
        usuario = Gson().fromJson(json, LoginResponse::class.java)

    }


    Column(Modifier.fillMaxSize()) {
        Text("Bienvenido ${usuario?.nombre}")
    }
}