package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.UserEntity
import com.example.myapplication.data.DatabaseModule
import com.example.myapplication.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = DatabaseModule.getDatabase(application).userDao()

    private val repository = UserRepository(userDao)

    // Fluxo observado pela interface (Compose)
    private val _users = MutableStateFlow<List<UserEntity>>(emptyList())
    val users: StateFlow<List<UserEntity>> get() = _users

    /**
     * Função usada pela tela.
     * Ela:
     * 1) Baixa os usuários da API
     * 2) Salva no banco
     * 3) Atualiza o StateFlow para a UI
     */
    fun refreshUsers() {
        viewModelScope.launch {
            try {
                repository.fetchAndSaveUsers() // baixa e salva
            } catch (e: Exception) {
                e.printStackTrace()
            }

            // Carrega do banco e envia para a UI
            val local = withContext(Dispatchers.IO) {
                repository.getAllUsers()
            }

            _users.value = local
        }
    }
}

