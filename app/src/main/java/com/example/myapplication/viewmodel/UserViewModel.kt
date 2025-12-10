package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.model.User
import com.example.myapplication.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "app_database"
    )
        .fallbackToDestructiveMigration()
        .build()

    private val repository = UserRepository(db.userDao())

    // register com callback (Boolean, String)
    fun register(nome: String, email: String, senha: String, callback: (Boolean, String) -> Unit) {
        // validações simples antes de tentar inserir
        if (nome.isBlank() || email.isBlank() || senha.isBlank()) {
            callback(false, "Preencha todos os campos.")
            return
        }

        // Você pode adicionar aqui checagem se o email já existe, se quiser.
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repository.registerUser(nome, email, senha)
                }
                callback(true, "OK")
            } catch (e: Exception) {
                e.printStackTrace()
                callback(false, "Erro ao cadastrar: ${e.message ?: "desconhecido"}")
            }
        }
    }

    fun login(email: String, senha: String, callback: (Boolean) -> Unit) {
        if (email.isBlank() || senha.isBlank()) {
            callback(false)
            return
        }

        viewModelScope.launch {
            val success = withContext(Dispatchers.IO) {
                repository.login(email, senha)
            }
            callback(success)
        }
    }

    fun getLoggedUser(): User? = repository.getLoggedUser()

    suspend fun getAllUsers(): List<User> = withContext(Dispatchers.IO) {
        repository.getAllUsers()
    }
}

