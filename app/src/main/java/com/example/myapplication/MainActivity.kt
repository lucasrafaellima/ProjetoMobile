package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.LoginScreen
import com.example.myapplication.ui.UserScreen
import com.example.myapplication.ui.CadastroScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                AppNavigator() // Controla a troca de telas
            }
        }
    }
}

@Composable
fun AppNavigator() {
    // Estado para controlar a tela atual
    var currentScreen by remember { mutableStateOf("login") }

    when (currentScreen) {
        "login" -> LoginScreen(
            onLoginSuccess = { currentScreen = "user" },
            onCreateAccount = { currentScreen = "cadastro" }
        )

        "cadastro" -> CadastroScreen(
            onCadastroConcluido = { currentScreen = "login" }
        )

        "user" -> UserScreen()
    }
}


