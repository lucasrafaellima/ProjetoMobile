package com.example.myapplication.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.viewmodel.UserViewModel
import com.example.myapplication.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(
    userViewModel: UserViewModel = viewModel()
) {
    var users by remember { mutableStateOf<List<User>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }

    // Usuário logado (não-suspend)
    val loggedUser = userViewModel.getLoggedUser()

    // Carrega a lista de usuários do banco uma única vez quando a tela abrir
    LaunchedEffect(Unit) {
        loading = true
        try {
            users = userViewModel.getAllUsers() // suspend
        } catch (e: Exception) {
            e.printStackTrace()
            users = emptyList()
        } finally {
            loading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Usuários") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Mostra informações do usuário logado (se houver)
            if (loggedUser != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Logado como:", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "Nome: ${loggedUser.name}")
                        Text(text = "Email: ${loggedUser.email}")
                    }
                }
            }

            // Estado de carregamento
            if (loading) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
                return@Column
            }

            // Lista de todos os usuários
            if (users.isEmpty()) {
                Text(
                    text = "Nenhum usuário cadastrado.",
                    modifier = Modifier.padding(top = 24.dp)
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(users) { user ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = "Nome: ${user.name}")
                                Text(text = "Email: ${user.email}")
                            }
                        }
                    }
                }
            }
        }
    }
}

