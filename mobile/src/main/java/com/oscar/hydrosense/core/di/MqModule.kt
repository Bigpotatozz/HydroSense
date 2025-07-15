package com.oscar.hydrosense.core.di

import android.util.Log
import com.google.gson.Gson
import com.hivemq.client.mqtt.MqttClient
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client
import com.oscar.hydrosense.home.data.network.response.SensorResponse
import dagger.Module
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.future.await
import java.util.UUID


fun provideMqttClient(): Flow<SensorResponse> = callbackFlow {
        val mqttClient = MqttClient.builder()
            .useMqttVersion5()
            .identifier(UUID.randomUUID().toString())
            .serverHost("42a15c797ff74200838c99684b8171eb.s1.eu.hivemq.cloud")
            .serverPort(8883)
            .sslWithDefaultConfig()
            .buildAsync();

        mqttClient
            .connectWith()
            .simpleAuth()
            .username("oscar")
            .password("Holaquehace12".toByteArray())
            .applySimpleAuth()
            .cleanStart(true)
            .send()
            .await()

        println("Conectado a HiveMQ Cloud con TLS y autenticación!")


        mqttClient
            .subscribeWith()
            .topicFilter("sensor/agua")
            .callback {message ->

                val optionalPayload = message.payload
                if (optionalPayload.isPresent) {
                    val buffer = optionalPayload.get()
                    val bytes = ByteArray(buffer.remaining())
                    buffer.get(bytes)
                    val jsonString = bytes.toString(Charsets.UTF_8)


                    try{
                        val sensorResponse = Gson().fromJson(jsonString, SensorResponse::class.java);
                        trySend(sensorResponse);
                    }catch (e: Exception){
                        Log.e("OSCAR", "Error al parsear JSON", e)
                    }

                    Log.i("OSCAR", "Data: $jsonString")
                }else{
                    Log.i("OSCAR", "Payload vacío")
                }
            }
            .send()
            .await()


        awaitClose() {
            Log.i("OSCAR", "🔌 Desconectando MQTT client")
            mqttClient.disconnect()
        }
    }
