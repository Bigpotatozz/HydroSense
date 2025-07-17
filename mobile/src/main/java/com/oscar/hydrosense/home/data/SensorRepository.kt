package com.oscar.hydrosense.home.data.network

import com.oscar.hydrosense.core.di.MqttClientProvider
import com.oscar.hydrosense.home.data.network.response.SensorResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SensorRepository @Inject constructor( private val mqttClientProvider: MqttClientProvider){
    private val _data = MutableSharedFlow<SensorResponse>(replay = 1);
    val sensorData: SharedFlow<SensorResponse> = _data;

    private var isStarted = false;

    suspend fun listening(){
        if(!isStarted){
            isStarted = true;
            mqttClientProvider.provideMqttClient().collect {
                _data.emit(it)
            }
        }
    }
}