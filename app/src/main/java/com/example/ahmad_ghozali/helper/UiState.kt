package com.example.ahmad_ghozali.helper

import com.example.ahmad_ghozali.response.Todo

sealed class UiState {
    object Loading: UiState()
    data class Success(val todos: List<Todo>): UiState()
    data class Error(val message: String): UiState()
}