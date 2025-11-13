package com.example.myapplication.api

import com.example.myapplication.model.Post
import com.example.myapplication.model.User
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("posts")
    suspend fun getPosts(): List<Post>
}



