package com.oscar.hydrosense.helpers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@Composable
fun Timer(seconds: Long, onFinish: () -> Unit){
    //SE DECLARA LA VARIABLE CON LOS SEGUNDOS QUE SE VA A TARDAR EL METODO
    var seconds by rememberSaveable { mutableStateOf(seconds) };

    //SE CREA UNA CORRUTINA
    LaunchedEffect(seconds) {
        //MIENTRAS QUE SECONDS SEA MAYOR A 0 SE EJECUTARA
        while (seconds > 0){
            //POR CADA ITERACION SE VA A RESTAR UN SEGUNDO
            delay(1000);
            //POR CADA ITERACION SE DESCUENTA UN SEGUNDO
            seconds--;
        }
        onFinish();
    }
}