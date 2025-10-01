package com.example.ahmad_ghozali.repository

import com.example.ahmad_ghozali.response.ApiService
import com.example.ahmad_ghozali.response.Todo

class TodoRepository(private val api: ApiService) {
    suspend fun getTodos(): List<Todo> = api.getTodos()
    suspend fun getTodo(id: Int): Todo = api.getTodo(id)
}