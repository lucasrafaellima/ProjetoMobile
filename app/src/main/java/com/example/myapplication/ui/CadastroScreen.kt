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
fun CadastroScreen(
    onCadastroConcluido: () -> Unit,
    userViewModel: UserViewModel = viewModel()
) {
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    var erro by remember { mutableStateOf("") }
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
            Text(text = "Cadastro", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

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

            if (erro.isNotEmpty()) {
                Text(
                    text = erro,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Button(
                onClick = {
                    carregando = true
                    erro = ""

                    // Chama o cadastro
                    userViewModel.register(nome, email, senha) { sucesso, mensagem ->
                        carregando = false
                        if (sucesso) {
                            onCadastroConcluido()
                        } else {
                            erro = mensagem
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !carregando
            ) {
                Text("Cadastrar")
            }

            if (carregando) {
                Spacer(modifier = Modifier.height(12.dp))
                CircularProgressIndicator()
            }
        }
    }
}

