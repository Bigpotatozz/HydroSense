package com.oscar.hydrosense.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import com.oscar.hydrosense.home.data.network.SensorRepository
import com.oscar.hydrosense.models.NotificacionHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SensorService: Service() {

    @Inject
    lateinit var sensorRepository: SensorRepository;

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob());

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        scope.launch {
            sensorRepository.listening()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}