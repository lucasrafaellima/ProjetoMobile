package com.example.myapplication.repository

import com.example.myapplication.api.RetrofitInstance
import com.example.myapplication.data.UserDao
import com.example.myapplication.data.UserEntity

class UserRepository(private val userDao: UserDao) {

    private val api = RetrofitInstance.api

    suspend fun fetchAndSaveUsers() {
        val usersFromApi = api.getUsers() // ✅ Agora reconhece getUsers()

        // ✅ converte os dados da API em entidades do Room
        val userEntities = usersFromApi.map { user ->
            UserEntity(
                name = user.name,
                email = user.email
            )
        }

        userDao.insertAll(userEntities)
    }

    suspend fun getAllUsers() = userDao.getAll()
}

