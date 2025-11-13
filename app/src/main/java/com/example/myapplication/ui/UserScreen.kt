package com.example.myapplication.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.viewmodel.UserViewModel
import com.example.myapplication.data.UserEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen() {
    val viewModel: UserViewModel = viewModel()
    var users by remember { mutableStateOf<List<UserEntity>>(emptyList()) }

    // Quando a tela for aberta, busca usuários da API e depois do banco local
    LaunchedEffect(Unit) {
        viewModel.syncUsersFromApi() // baixa da API e salva no Room
        users = viewModel.getLocalUsers() // recupera do banco local
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Usuários da API + Banco") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (users.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            } else {
                LazyColumn {
                    items(users) { user: UserEntity ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
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

