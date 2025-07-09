package com.oscar.hydrosense.account.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import com.google.gson.Gson
import com.oscar.hydrosense.login.data.network.dataStore
import com.oscar.hydrosense.login.data.network.response.LoginResponse
import com.oscar.hydrosense.theme.funnelSans
import com.oscar.hydrosense.ui.PersonalizedDropdownField
import com.oscar.hydrosense.ui.PersonalizedNumberField
import com.oscar.hydrosense.ui.PersonalizedTextField
import com.oscar.hydrosense.utils.paises
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun AccountScreen( accountViewModel: AccountViewModel, navController: NavController) {
    Account( accountViewModel, navController);
}

@OptIn(ExperimentalMaterial3Api::class)
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


        var expandedDropdown by remember { mutableStateOf(false) };



        Column(Modifier.fillMaxSize().padding(31.dp)) {
            Text(text = "Mi perfil",
                style = TextStyle(fontFamily = funnelSans, fontSize = 32.sp, fontWeight = FontWeight.SemiBold))

            Text(text = "Visualiza y edita tu informacion",
                style = TextStyle(fontFamily = funnelSans, fontSize = 15.sp, fontWeight = FontWeight.Light))


            Spacer(Modifier.padding(30.dp))


            PersonalizedTextField(modifier = Modifier, nombre, Icons.Filled.Person, "Nombre") {
                accountViewModel.setNombre(it)
            }
            Spacer(Modifier.padding(5.dp))

            PersonalizedTextField(modifier = Modifier, apellido_paterno, Icons.Filled.Person, "Apellido paterno") {
                    accountViewModel.setApellido_paterno(it)
            }

            Spacer(Modifier.padding(5.dp))

            PersonalizedTextField(modifier = Modifier, apellido_materno, Icons.Filled.Person, "Apellido materno") {
                    accountViewModel.setApellido_materno(it)
            }

            Spacer(Modifier.padding(5.dp))

            PersonalizedTextField(modifier = Modifier, telefono, Icons.Filled.Phone, "telefono") {
                    accountViewModel.setTelefono(it)
            }

            Spacer(Modifier.padding(5.dp))

            Row {
                PersonalizedNumberField(modifier = Modifier.weight(0.5f), edad, Icons.Filled.Person, "Edad") {
                    accountViewModel.setEdad(it)
                }

                Spacer(Modifier.padding(5.dp))

                Box(Modifier.fillMaxWidth().zIndex(1f).weight(0.5f)){
                    ExposedDropdownMenuBox(expanded = expandedDropdown,
                        onExpandedChange = {
                            Log.i("OSCAR", "${expandedDropdown}")
                            expandedDropdown = !expandedDropdown},
                    ) {

                        PersonalizedDropdownField(modifier = Modifier.menuAnchor().fillMaxWidth(),
                            icono = Icons.Filled.Flag,
                            value = pais,
                            placeholder = "Pais",
                            trailingIcon = expandedDropdown) {}
                        ExposedDropdownMenu(expanded = expandedDropdown,
                            onDismissRequest = {expandedDropdown = false},
                            modifier = Modifier.height(200.dp)) {
                            paises.forEach {
                                DropdownMenuItem(text = {Text(it)}, onClick = {
                                    accountViewModel.setPais(it)
                                    expandedDropdown = false;
                                    Log.i("OSCAR", "${it}")
                                })
                            }
                        }
                    }


                }

            }

            Spacer(Modifier.padding(5.dp))



            PersonalizedTextField(modifier = Modifier,value = correo, icono = Icons.Filled.AlternateEmail, placeholder = "Correo") {
                accountViewModel.setCorreo(it)
            }

            Spacer(Modifier.padding(5.dp))

            PersonalizedTextField(modifier = Modifier,value = contrasenia, icono = Icons.Filled.Visibility, placeholder = "Contraseña") {
                accountViewModel.setContrasenia(it)
            }

            Spacer(Modifier.padding(5.dp))

            PersonalizedTextField(modifier = Modifier,value = contrasenia2, icono = Icons.Filled.Visibility, placeholder = "Confirmar contraseña") {
               contrasenia2 = it

               if(contrasenia2 == contrasenia){
                   samePassword = true
               }else{
                   samePassword = false
               }
            }


            Spacer(Modifier.padding(5.dp))

            Button(onClick = {
                if(samePassword == true){
                    Log.i("OSCAR", "${correo} ${contrasenia}")
                    accountViewModel.editarUsuario(usuario2?.idUsuario ?: 0,nombre,correo,contrasenia,telefono,edad,pais,apellido_paterno,apellido_materno);
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Usuario editado con exito")
                    }

                }else{
                    Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }

            },
                modifier = Modifier.fillMaxWidth().height(47.dp),
                colors = ButtonDefaults.buttonColors( containerColor = Color(0xFF1A2130))) {
                Text("Editar informacion",
                    color = Color(0xFFFDFFE2),
                    style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Bold))
            }


            /*
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )
            */

        }

    }
}