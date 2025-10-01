package com.example.ahmad_ghozali.response

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("todos")
    suspend fun getTodos(): List<Todo>

    @GET("todos/{id}")
    suspend fun getTodo(@Path("id") id: Int): Todo
}