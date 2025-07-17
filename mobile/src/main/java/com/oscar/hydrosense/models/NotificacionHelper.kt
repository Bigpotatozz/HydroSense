package com.oscar.hydrosense.models

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import javax.inject.Inject
import com.oscar.hydrosense.R
import dagger.hilt.android.qualifiers.ApplicationContext


class NotificacionHelper @Inject constructor(@ApplicationContext private val context: Context) {


    fun showNotification(title: String, message: String){


        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val canal = NotificationChannel(
                "canal_id",
                "Notificaciones generales",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            notificationManager.createNotificationChannel(canal)
        }

        val notificacion = NotificationCompat
            .Builder(context, "canal_id")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notificacion);
    }

}