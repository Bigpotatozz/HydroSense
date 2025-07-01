package com.oscar.hydrosense

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.oscar.hydrosense.login.ui.Login
import com.oscar.hydrosense.login.ui.LoginScreen
import com.oscar.hydrosense.login.ui.LoginViewModel
import com.oscar.hydrosense.registro.ui.RegisterViewModel
import com.oscar.hydrosense.registro.ui.Registro
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

                Scaffold {
                    innerPadding ->
                    Column(Modifier.fillMaxSize().padding(innerPadding)) {
                        Login(Modifier, loginViewModel);

                    }
                }

            }
        }
    }
}

// The DataStore should be a singleton, so it's better to define it at the top level of the file,
// outside any class, or as a static property in a companion object.
// This makes it accessible throughout your application and ensures only one instance exists.
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user");

