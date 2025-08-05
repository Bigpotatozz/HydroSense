package com.oscar.hydrosense.login.data.network

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.oscar.hydrosense.login.data.network.request.LoginRequest
import com.oscar.hydrosense.login.data.network.response.LoginResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "usuario");

class LoginRepository @Inject constructor(private val loginService: LoginService, @ApplicationContext private val context: Context){



    suspend fun login(loginRequest: LoginRequest): LoginResponse? {

        val response = loginService.login(loginRequest);

        Log.i("OSCAREPOSITORY", "${response}")

        if(response != null){
            val json = Gson().toJson(response);
            val key = stringPreferencesKey("login_response");
            context.dataStore.edit { prefs -> prefs[key] = json };
        }


        return response;

    }

}