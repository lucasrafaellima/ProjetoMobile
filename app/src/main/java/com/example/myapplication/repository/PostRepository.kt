package com.example.myapplication.repository

import com.example.myapplication.api.ApiService
import com.example.myapplication.model.Post

class PostRepository(private val apiService: ApiService) {
    suspend fun getPosts(): List<Post> {
        return apiService.getPosts() // ✅ agora funciona
    }
}

