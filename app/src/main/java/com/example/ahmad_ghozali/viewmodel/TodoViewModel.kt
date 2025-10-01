package com.example.ahmad_ghozali.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ahmad_ghozali.helper.UiState
import com.example.ahmad_ghozali.repository.TodoRepository
import com.example.ahmad_ghozali.response.Todo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodoViewModel(private val repo: TodoRepository): ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val _selectedTodo = MutableStateFlow<Todo?>(null)
    val selectedTodo: StateFlow<Todo?> = _selectedTodo

    init {
        fetchTodos()
    }

    fun fetchTodos() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val list = repo.getTodos()
                _uiState.value = UiState.Success(list)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun loadTodoDetail(id: Int) {
        viewModelScope.launch {
            _selectedTodo.value = null
            try {
                val t = repo.getTodo(id)
                _selectedTodo.value = t
            } catch (e: Exception) {
                // keep null
            }
        }
    }

    fun selectLocal(todo: Todo) {
        _selectedTodo.value = todo
    }
}