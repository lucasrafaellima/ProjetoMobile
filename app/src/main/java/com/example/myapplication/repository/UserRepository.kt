package com.example.myapplication.repository

import com.example.myapplication.data.UserDao
import com.example.myapplication.model.User

class UserRepository(private val userDao: UserDao) {

    private var loggedUser: User? = null

    suspend fun registerUser(name: String, email: String, password: String) {
        val user = User(name = name, email = email, password = password)
        userDao.insert(user)
    }

    suspend fun login(email: String, password: String): Boolean {
        val user = userDao.login(email, password)
        return if (user != null) {
            loggedUser = user
            true
        } else {
            false
        }
    }

    fun getLoggedUser(): User? = loggedUser

    suspend fun getAllUsers(): List<User> = userDao.getAll()
}


