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

//CREA LA CLASE QUE HACE LAS NOTIFICACIONES
class NotificacionHelper @Inject constructor(@ApplicationContext private val context: Context) {

    //FUNCION QUE CONSTRUYE Y ENVIA LA NOTIFICACION
    fun showNotification(title: String, message: String){

        //SE USA EL CONTEXTO Y MANDA A LLAMAR AL SERVICIO QUE ENVIA LAS NOTIFICACIONES
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //VERIFICA SI LA VERSION DE ANDROID ES MAYOR A LA OREO
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //CONFIGURA UN CANAL QUE SE NECESITA PARA ENVIAR LA NOTIFICACION
            val canal = NotificationChannel(
                "canal_id",
                "Notificaciones generales",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            //CREA EL CANAL
            notificationManager.createNotificationChannel(canal)
        }

        //SE DEFINE EL CONTENIDO Y CANAL DE LA NOTIFICACION
        val notificacion = NotificationCompat
            .Builder(context, "canal_id")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .build()

        //SE ENVIA LA NOTIFICACION
        notificationManager.notify(1, notificacion);
    }

}