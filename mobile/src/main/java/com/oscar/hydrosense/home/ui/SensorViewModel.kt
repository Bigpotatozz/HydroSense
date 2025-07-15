package com.oscar.hydrosense.home.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hivemq.client.mqtt.MqttClient
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client
import com.oscar.hydrosense.core.di.provideMqttClient
import com.oscar.hydrosense.home.data.network.response.SensorResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class SensorViewModel @Inject constructor(): ViewModel() {
        val flow = provideMqttClient()
            .stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = null)
}