package com.example.ahmad_ghozali.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.ahmad_ghozali.master.RetrofitInstance
import com.example.ahmad_ghozali.repository.TodoRepository
import com.example.ahmad_ghozali.ui.host.AppNavHost
import com.example.ahmad_ghozali.ui.theme.MasterDetailTheme
import com.example.ahmad_ghozali.viewmodel.TodoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // 🔹 aktifkan splash API
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        // create repository and vm factory
        val repo = TodoRepository(RetrofitInstance.api)
        val factory = object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return TodoViewModel(repo) as T
            }
        }

        setContent {
            MasterDetailTheme {
                val vm: TodoViewModel = viewModel(factory = factory)
                AppNavHost(viewModel = vm)
            }
        }
    }
}
