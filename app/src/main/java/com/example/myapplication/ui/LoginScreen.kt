package com.example.myapplication.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.viewmodel.UserViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onCreateAccount: () -> Unit,
    userViewModel: UserViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    var mensagemErro by remember { mutableStateOf<String?>(null) }
    var carregando by remember { mutableStateOf(false) }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(text = "Login", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = senha,
                onValueChange = { senha = it },
                label = { Text("Senha") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                enabled = !carregando,
                onClick = {
                    if (email.isBlank() || senha.isBlank()) {
                        mensagemErro = "Preencha todos os campos."
                        return@Button
                    }

                    carregando = true

                    userViewModel.login(email, senha) { sucesso ->
                        carregando = false
                        if (sucesso) {
                            onLoginSuccess()
                        } else {
                            mensagemErro = "Email ou senha incorretos!"
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (carregando) "Entrando..." else "Entrar")
            }

            Spacer(modifier = Modifier.height(10.dp))

            TextButton(onClick = { onCreateAccount() }) {
                Text("Criar conta")
            }

            mensagemErro?.let {
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
