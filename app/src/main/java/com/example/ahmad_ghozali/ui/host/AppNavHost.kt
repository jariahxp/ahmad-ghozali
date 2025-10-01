package com.example.ahmad_ghozali.ui.host

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.ahmad_ghozali.ui.screen.ListScreen
import com.example.ahmad_ghozali.ui.screen.detail.DetailScreen
import com.example.ahmad_ghozali.viewmodel.TodoViewModel

@Composable
fun AppNavHost(viewModel: TodoViewModel, modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "list", modifier = modifier) {
        composable("list") {
            ListScreen(
                viewModel = viewModel,
                onItemClick = { id ->
                    navController.navigate("detail/$id")
                }
            )
        }
        composable(
            route = "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            DetailScreen(id = id, viewModel = viewModel, onBack = { navController.popBackStack() })
        }
    }
}