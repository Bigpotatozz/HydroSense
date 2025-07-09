package com.oscar.hydrosense.registro.ui

import android.content.Context
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.oscar.hydrosense.theme.funnelSans
import com.oscar.hydrosense.ui.PersonalizedDropdownField
import com.oscar.hydrosense.ui.PersonalizedNumberField
import com.oscar.hydrosense.ui.PersonalizedTextField
import com.oscar.hydrosense.utils.paises
import kotlinx.coroutines.launch


@Composable
fun RegistroScreen( modifier: Modifier, registerViewModel: RegisterViewModel, navController: NavController){


    Registro(registerViewModel, navController);
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Registro(registerViewModel: RegisterViewModel, navController: NavController){

    var context: Context = LocalContext.current;


    val nombre by registerViewModel.nombre.observeAsState(initial = "");
    val correo by registerViewModel.correo.observeAsState(initial = "");
    val contrasenia by registerViewModel.contrasenia.observeAsState(initial = "");
    val telefono by registerViewModel.telefono.observeAsState(initial = "");
    val edad by registerViewModel.edad.observeAsState(initial = "");
    val pais by registerViewModel.pais.observeAsState(initial = "");
    val apellido_paterno by registerViewModel.apellido_paterno.observeAsState(initial = "");
    val apellido_materno by registerViewModel.apellido_materno.observeAsState(initial = "");

    var contrasenia2 by rememberSaveable { mutableStateOf("") };
    var samePassword by rememberSaveable { mutableStateOf(false) };

    val coroutineScope = rememberCoroutineScope();



    var termsAndConditions by rememberSaveable { mutableStateOf(false) }
    var expandedDropdown by remember { mutableStateOf(false) };
    Column(Modifier.fillMaxSize().padding(31.dp)) {

        Text(text = "Registro",
            style = TextStyle(fontFamily = funnelSans, fontSize = 32.sp, fontWeight = FontWeight.SemiBold))

        Text(text = "Rellena el formulario",
            style = TextStyle(fontFamily = funnelSans, fontSize = 15.sp, fontWeight = FontWeight.Light))

        Spacer(Modifier.padding(30.dp))



        PersonalizedTextField(modifier = Modifier, nombre, Icons.Filled.Person, "Nombre") {
            registerViewModel.setNombre(it)
        }
        Spacer(Modifier.padding(5.dp))

        PersonalizedTextField(modifier = Modifier, apellido_paterno, Icons.Filled.Person, "Apellido paterno") {
            registerViewModel.setApellido_paterno(it)
        }


        Spacer(Modifier.padding(5.dp))

        PersonalizedTextField(modifier = Modifier, apellido_materno, Icons.Filled.Person, "Apellido materno") {
            registerViewModel.setApellido_materno(it)
        }



        Spacer(Modifier.padding(5.dp))

        PersonalizedTextField(modifier = Modifier, telefono, Icons.Filled.Phone, "telefono") {
            registerViewModel.setTelefono(it)
        }

        Spacer(Modifier.padding(5.dp))


        Row {
            PersonalizedNumberField(modifier = Modifier.weight(0.5f), edad, Icons.Filled.Person, "Edad") {
                registerViewModel.setEdad(it)
            }

            Spacer(Modifier.padding(5.dp))

            Box(Modifier.fillMaxWidth().zIndex(1f).weight(0.5f)){
                ExposedDropdownMenuBox (expanded = expandedDropdown,
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
                                registerViewModel.setPais(it)
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
            registerViewModel.setCorreo(it)
        }

        Spacer(Modifier.padding(5.dp))

        PersonalizedTextField(modifier = Modifier,value = contrasenia, icono = Icons.Filled.Visibility, placeholder = "Contraseña") {
            registerViewModel.setContrasenia(it)
        }

        Spacer(Modifier.padding(5.dp))

        PersonalizedTextField(modifier = Modifier, value = contrasenia2, icono = Icons.Filled.Visibility, placeholder = "Confirmar contraseña") {
            contrasenia2 = it

            if(contrasenia2 == contrasenia){
                samePassword = true
            }else{
                samePassword = false
            }

        }
        Row (verticalAlignment = Alignment.CenterVertically){
            Checkbox(onCheckedChange = {
                 termsAndConditions = !termsAndConditions
            },
                checked = termsAndConditions)
            Text("Aceptar terminos y condiciones")
        }



        Spacer(Modifier.padding(5.dp))

        Button(onClick = {


            if(samePassword == true && termsAndConditions == true){
                Log.i("OSCAR", "${correo} ${contrasenia}")
                registerViewModel.registrarUsuario(nombre,correo,contrasenia,telefono,edad,pais,apellido_paterno,apellido_materno);
                navController.navigate("Login")

            }else{
                Toast.makeText(context, "Las contraseñas no coinciden o no ha aceptado los terminos y condiciones", Toast.LENGTH_SHORT).show();
            }

        },
            modifier = Modifier.fillMaxWidth().height(47.dp),
            colors = ButtonDefaults.buttonColors( containerColor = Color(0xFF1A2130))) {
            Text("Registrarse",
                color = Color(0xFFFDFFE2),
                style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Bold))
        }


    }
}