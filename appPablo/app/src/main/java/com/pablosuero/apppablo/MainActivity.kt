package com.pablosuero.apppablo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pablosuero.apppablo.data.model.AuthManager
import com.pablosuero.apppablo.ui.navigation.AppNavigation
import com.pablosuero.apppablo.ui.theme.AppPabloTheme

class MainActivity : ComponentActivity() {
    val auth = AuthManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppPabloTheme {
                AppNavigation(auth)
            }
        }
    }
}