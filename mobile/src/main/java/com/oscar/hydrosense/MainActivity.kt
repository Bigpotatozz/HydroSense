package com.oscar.hydrosense

import android.content.Context
import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.core.DataStore // Correct import for DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.oscar.hydrosense.login.ui.HomeScreen
import com.oscar.hydrosense.login.ui.Login
import com.oscar.hydrosense.login.ui.LoginScreen
import com.oscar.hydrosense.login.ui.LoginViewModel
import com.oscar.hydrosense.registro.ui.RegisterViewModel
import com.oscar.hydrosense.registro.ui.Registro
import com.oscar.hydrosense.registro.ui.RegistroScreen
import com.oscar.hydrosense.theme.HydroSenseTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels();
    private val registerViewModel: RegisterViewModel by viewModels();

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user");

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState);
        setContent {

            HydroSenseTheme {
                    Column(Modifier.fillMaxSize().padding(WindowInsets.systemBars.asPaddingValues())) {
                        AppNavigation();

                    }


            }
        }
    }


    @Composable
    fun AppNavigation(){
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "login"){
            composable("login") {
                Login(modifier = Modifier, loginViewModel = loginViewModel, navController = navController)
            }

            composable("home") {
                HomeScreen(navController);
            }

            composable("register") {
                RegistroScreen(modifier = Modifier, registerViewModel = registerViewModel, navController)
            }
        }
    }


}





