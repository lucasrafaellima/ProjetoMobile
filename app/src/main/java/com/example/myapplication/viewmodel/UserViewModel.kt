package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = Room.databaseBuilder(
        application,
        com.example.myapplication.data.AppDatabase::class.java,
        "app_database"
    ).build().userDao()

    private val repository = UserRepository(userDao)

    fun syncUsersFromApi() {
        viewModelScope.launch {
            repository.fetchAndSaveUsers()
        }
    }

    suspend fun getLocalUsers() = repository.getAllUsers()
}
