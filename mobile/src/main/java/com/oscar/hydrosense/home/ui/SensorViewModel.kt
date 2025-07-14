package com.oscar.hydrosense.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hivemq.client.mqtt.MqttClient
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client
import com.oscar.hydrosense.core.di.provideMqttClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SensorViewModel @Inject constructor(): ViewModel() {

    init {
        connectToMqtt();

    }

    private fun connectToMqtt() {
        viewModelScope.launch {
            provideMqttClient();
        }
    }



}