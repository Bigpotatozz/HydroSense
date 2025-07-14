package com.oscar.hydrosense.home.data.network

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext

import com.oscar.hydrosense.home.data.network.response.SensorResponse
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton


