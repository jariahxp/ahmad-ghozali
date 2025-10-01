package com.example.ahmad_ghozali.ui.screen.detail

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ahmad_ghozali.response.Todo
import com.example.ahmad_ghozali.viewmodel.TodoViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(id: Int, viewModel: TodoViewModel, onBack: () -> Unit) {
    val selected by viewModel.selectedTodo.collectAsState()

    // 🔹 Atur status bar agar matching dengan UI
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color(0xFF0D47A1), // deep blue
            darkIcons = false
        )
    }

    // load data ketika screen dibuka
    LaunchedEffect(id) {
        viewModel.loadTodoDetail(id)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Todo Detail",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF0D47A1)
                )
            )
        }
    ) { inner ->
        Box(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(Color(0xFF42A5F5), Color(0xFF0D47A1))
                    )
                )
        ) {
            // 🔹 Loading state dengan animasi halus
            AnimatedVisibility(
                visible = selected == null,
                enter = fadeIn() + expandIn(),
                exit = fadeOut() + shrinkOut(),
                modifier = Modifier.align(Alignment.Center)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = Color.White, strokeWidth = 3.dp)
                    Spacer(Modifier.height(12.dp))
                    Text("Loading detail...", color = Color.White, style = MaterialTheme.typography.bodyMedium)
                }
            }

            // 🔹 Content state
            AnimatedVisibility(
                visible = selected != null,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
                exit = fadeOut(),
                modifier = Modifier.align(Alignment.TopCenter)
            ) {
                selected?.let { DetailContent(todo = it) }
            }
        }
    }
}

@Composable
fun DetailContent(todo: Todo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            // 🔹 Judul
            Text(
                text = todo.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF0D47A1)
            )

            Spacer(Modifier.height(16.dp))

            // 🔹 Informasi ID & User
            Text("Todo ID: ${todo.id}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            Text("User ID: ${todo.userId}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)

            Spacer(Modifier.height(20.dp))

            // 🔹 Status Todo (Completed / Pending)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(
                        color = if (todo.completed) Color(0x1A4CAF50) else Color(0x1AFF9800),
                        shape = CircleShape
                    )
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Icon(
                    imageVector = if (todo.completed) Icons.Default.CheckCircle else Icons.Default.Schedule,
                    contentDescription = null,
                    tint = if (todo.completed) Color(0xFF4CAF50) else Color(0xFFFF9800),
                    modifier = Modifier.size(26.dp)
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = if (todo.completed) "Completed" else "Pending",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = if (todo.completed) Color(0xFF4CAF50) else Color(0xFFFF9800)
                )
            }

            Spacer(Modifier.height(24.dp))

            // 🔹 Progress Bar sebagai gambaran "todo completion"
            LinearProgressIndicator(
                progress = if (todo.completed) 1f else 0.4f, // contoh: pending dianggap 40% progress
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = if (todo.completed) Color(0xFF4CAF50) else Color(0xFFFF9800),
                trackColor = Color(0x22000000)
            )

            Spacer(Modifier.height(24.dp))

            // 🔹 Info tambahan (tanggal dibuat / deadline dummy)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Timer, contentDescription = null, tint = Color(0xFF1565C0))
                Spacer(Modifier.width(8.dp))
                Text(
                    "Created (dummy): ${getTodayDate()}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF424242)
                )
            }
        }
    }
}

private fun getTodayDate(): String {
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return sdf.format(Date())
}
