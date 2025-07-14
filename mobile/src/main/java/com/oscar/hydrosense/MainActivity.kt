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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.core.DataStore // Correct import for DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.oscar.hydrosense.account.ui.Account
import com.oscar.hydrosense.account.ui.AccountScreen
import com.oscar.hydrosense.account.ui.AccountViewModel
import com.oscar.hydrosense.home.ui.HomeScreen
import com.oscar.hydrosense.home.ui.SensorViewModel
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
    private val sensorViewModel: SensorViewModel by viewModels();


    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user");

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState);
        setContent {

            val navController = rememberNavController();
            val noBottomBar = listOf<String>("login", "register");
            val currentBackStackEntry by navController.currentBackStackEntryAsState();
            val rutactual = currentBackStackEntry?.destination?.route
            val mostrarBottomBar = rutactual !in noBottomBar;

            HydroSenseTheme {
                Scaffold(bottomBar = {

                    if(mostrarBottomBar){
                        Navbar(navController)
                    }
                }) { innerPadding ->
                    HomeScreen(Modifier.padding(innerPadding), navController, sensorViewModel);
                }


            }
        }
    }


    @Composable
    fun AppNavigation(modifier:Modifier, navController: NavHostController){

        NavHost(navController = navController, startDestination = "login", modifier = modifier){
            composable("login") {
                LoginScreen(modifier = modifier, loginViewModel = loginViewModel, navController = navController)
            }

            composable("home") {
                HomeScreen(modifier,navController, sensorViewModel);
            }

            composable("register") {
                RegistroScreen(modifier = modifier, registerViewModel = registerViewModel, navController)
            }

            composable("perfil") {
                AccountScreen( modifier, accountViewModel = accountViewModel, navController);
            }
        }
    }



    @Composable
    fun Navbar(navController: NavController) {

        var selectedItem by rememberSaveable { mutableStateOf(0)}
        val items = listOf("home", "perfil");
        val selectedIcons = listOf(Icons.Filled.Home, Icons.Filled.AccountCircle)
        val unselectedIcons = listOf(Icons.Filled.Home, Icons.Filled.AccountCircle)

        NavigationBar(modifier = Modifier,
            containerColor = NavigationBarDefaults.containerColor,
            contentColor = MaterialTheme.colorScheme.contentColorFor(NavigationBarDefaults.containerColor)) {

            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = { Icon( if (selectedItem == index) selectedIcons[index] else unselectedIcons[index], contentDescription = item)},
                    label = { Text(item) },
                    selected = selectedItem == index,
                    onClick = {
                        selectedItem = index
                        navController.navigate("${item}")
                    },
                    );
            }

        }


    }

}





