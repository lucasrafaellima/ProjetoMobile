package com.example.myapplication.api

import com.example.myapplication.model.Post
import retrofit2.http.GET

interface PostApiService {
    @GET("posts")
    suspend fun getPosts(): List<Post>
}
