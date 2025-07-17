package com.oscar.hydrosense.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import com.oscar.hydrosense.core.di.provideMqttClient
import com.oscar.hydrosense.models.NotificacionHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SensorService: Service() {

    //Se inyecta el helper de notificaciones
    @Inject lateinit var notificationHelper: NotificacionHelper;

    //se declara la corrutina donde se recibiran los datos
    private val scope = CoroutineScope(Dispatchers.IO);


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int{

        //se lanza la corrutina
        scope.launch {
            //se suscribe al flow del cliente mqtt
            provideMqttClient().collectLatest {
                it?.let {
                    Log.i("OSCAR", "Data: ${it.ph}")
                }
            }
        }

        //alch no se que hace
        return START_STICKY
    }

    //Es para el bind de los servicios pero en este caso no se usa
    override fun onBind(intent: Intent?): IBinder? = null

}