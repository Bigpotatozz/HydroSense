package com.oscar.hydrosense

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.oscar.hydrosense.login.ui.LoginScreen
import com.oscar.hydrosense.login.ui.LoginViewModel
import com.oscar.hydrosense.theme.HydroSenseTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels();

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            HydroSenseTheme {

                Scaffold {
                    innerPadding ->
                    Column(Modifier.fillMaxSize().padding(innerPadding)) {
                        LoginScreen(Modifier, loginViewModel);
                    }
                }

            }
        }
    }
}
