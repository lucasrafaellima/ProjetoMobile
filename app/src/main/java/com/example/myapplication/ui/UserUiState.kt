package com.example.myapplication.ui

import com.example.myapplication.model.User

data class UserUiState(
    val users: List<User> = emptyList(), // ← aqui mudou
    val loading: Boolean = false,
    val error: String? = null
)
