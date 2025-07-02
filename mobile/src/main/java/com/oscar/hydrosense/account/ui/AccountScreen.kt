package com.oscar.hydrosense.account.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import com.google.gson.Gson
import com.oscar.hydrosense.login.data.network.response.LoginResponse
import com.oscar.hydrosense.login.ui.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun AccountScreen(modifier: Modifier, navController: NavController, accountViewModel: AccountViewModel) {
    Account(accountViewModel = accountViewModel, navController = navController);
}


@Composable
fun Account(accountViewModel: AccountViewModel, navController: NavController){
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() };
    val coroutineScope = rememberCoroutineScope();
    Column(Modifier.fillMaxSize()) {

        var usuario2 by remember { mutableStateOf<LoginResponse?>(null); };

        val nombre by accountViewModel.nombre.observeAsState(initial = "");
        val correo by accountViewModel.correo.observeAsState(initial = "");
        val contrasenia by accountViewModel.contrasenia.observeAsState(initial = "");
        val telefono by accountViewModel.telefono.observeAsState(initial = "");
        val edad by accountViewModel.edad.observeAsState(initial = "");
        val pais by accountViewModel.pais.observeAsState(initial = "");
        val apellido_paterno by accountViewModel.apellido_paterno.observeAsState(initial = "");
        val apellido_materno by accountViewModel.apellido_materno.observeAsState(initial = "");

        var contrasenia2 by rememberSaveable { mutableStateOf("") };
        var samePassword by rememberSaveable { mutableStateOf(false) };


        LaunchedEffect(Unit) {
            val prefs = context.dataStore.data.first();
            val json = prefs[stringPreferencesKey("login_response")];
            usuario2 = Gson().fromJson(json, LoginResponse::class.java)

            accountViewModel.setNombre(usuario2?.nombre ?: "")
            accountViewModel.setCorreo(usuario2?.correo ?: "")
            accountViewModel.setTelefono(usuario2?.telefono ?: "")
            accountViewModel.setEdad(usuario2?.edad.toString())
            accountViewModel.setPais(usuario2?.pais ?: "")
            accountViewModel.setApellido_paterno(usuario2?.apellido_paterno ?: "")
            accountViewModel.setApellido_materno(usuario2?.apellido_materno ?: "")

        }

        Column(Modifier.fillMaxSize().padding(31.dp)) {
            Text("ACTUALIZAR INFORMACION")

            Spacer(Modifier.padding(49.dp))

            OutlinedTextField(value = nombre, onValueChange = {
                accountViewModel.setNombre(it)
            }, placeholder = {Text("Nombre")},
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth().height(51.dp)
            )

            OutlinedTextField(value = apellido_paterno, onValueChange = {
                accountViewModel.setApellido_paterno(it)
            }, placeholder = {Text("Apellido paterno")},
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth().height(51.dp)
            )

            OutlinedTextField(value = apellido_materno, onValueChange = {
                accountViewModel.setApellido_materno(it)
            }, placeholder = {Text("Apellido paterno")},
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth().height(51.dp)
            )

            OutlinedTextField(value = telefono, onValueChange = {
                accountViewModel.setTelefono(it)
            }, placeholder = {Text("Telefono")},
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth().height(51.dp)
            )

            OutlinedTextField(value = edad, onValueChange = {
                accountViewModel.setEdad(it)
            }, placeholder = {Text("Edad")},
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth().height(51.dp)
            )

            OutlinedTextField(value = pais
                , onValueChange = {
                    accountViewModel.setPais(it)
                }, placeholder = {Text("Pais")},
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth().height(51.dp)
            )

            OutlinedTextField(value = correo, onValueChange = {
                accountViewModel.setCorreo(it)
            }, placeholder = {Text("Correo")},
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth().height(51.dp)
            )

            OutlinedTextField(value = contrasenia, onValueChange = {
                accountViewModel.setContrasenia(it)
            }, placeholder = {Text("Contrasenia")},
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth().height(51.dp)
            )

            OutlinedTextField(value = contrasenia2, onValueChange = {

                contrasenia2 = it

                if(contrasenia2 == contrasenia){
                    samePassword = true
                }else{
                    samePassword = false
                }
            }, placeholder = {Text("Confirmar contrasenia")},
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth().height(51.dp)
            )



            Button(onClick = {
                if(samePassword == true){
                    Log.i("OSCAR", "${correo} ${contrasenia}")
                    accountViewModel.editarUsuario(usuario2?.idUsuario ?: 0,nombre,correo,contrasenia,telefono,edad,pais,apellido_paterno,apellido_materno);
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Usuario editado con exito")
                    }

                }else{
                    Log.i("OSCAR", "contraseña no coincidente")
                }

            },
                shape = RoundedCornerShape(22.dp),
                modifier = Modifier.fillMaxWidth()) {
                Text("Editar informacion")
            }


            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )
        }

    }
}