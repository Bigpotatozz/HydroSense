package com.oscar.hydrosense

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.core.DataStore // Correct import for DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.oscar.hydrosense.account.ui.Account
import com.oscar.hydrosense.account.ui.AccountScreen
import com.oscar.hydrosense.account.ui.AccountViewModel
import com.oscar.hydrosense.home.ui.HomeScreen
import com.oscar.hydrosense.login.ui.Login
import com.oscar.hydrosense.login.ui.LoginScreen
import com.oscar.hydrosense.login.ui.LoginViewModel
import com.oscar.hydrosense.registro.ui.RegisterViewModel
import com.oscar.hydrosense.registro.ui.RegistroScreen
import com.oscar.hydrosense.theme.HydroSenseTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels();
    private val registerViewModel: RegisterViewModel by viewModels();
    private val accountViewModel: AccountViewModel by viewModels();

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user");

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState);
        setContent {

            HydroSenseTheme {
                    Column(Modifier.fillMaxSize().padding(WindowInsets.systemBars.asPaddingValues())) {
                        AccountScreen()
                    }


            }
        }
    }


    @Composable
    fun AppNavigation(){
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "login"){
            composable("Login") {
                LoginScreen(modifier = Modifier, loginViewModel = loginViewModel, navController = navController)
            }

            composable("Home") {
                HomeScreen(navController);
            }

            composable("Register") {
                RegistroScreen(modifier = Modifier, registerViewModel = registerViewModel, navController)
            }

            composable("Account") {
                AccountScreen();
            }
        }
    }


}





