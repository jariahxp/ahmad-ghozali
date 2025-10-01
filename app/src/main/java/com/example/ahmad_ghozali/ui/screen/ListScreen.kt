package com.example.ahmad_ghozali.ui.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ahmad_ghozali.helper.UiState
import com.example.ahmad_ghozali.viewmodel.TodoViewModel

enum class TodoFilter { ALL, COMPLETED, PENDING }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(viewModel: TodoViewModel, onItemClick: (Int) -> Unit) {
    val state by viewModel.uiState.collectAsState()
    var selectedFilter by remember { mutableStateOf(TodoFilter.ALL) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Ahmad Ghozali",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.background(
                    brush = Brush.horizontalGradient(
                        listOf(Color(0xFF1976D2), Color(0xFF2196F3))
                    )
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(Color(0xFFE3F2FD), Color(0xFFBBDEFB))
                    )
                )
        ) {
            when (state) {
                is UiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is UiState.Error -> {
                    val msg = (state as UiState.Error).message
                    Column(
                        Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Error: $msg", fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = { viewModel.fetchTodos() }) { Text("Retry") }
                    }
                }

                is UiState.Success -> {
                    val todos = (state as UiState.Success).todos

                    val completedCount = todos.count { it.completed }
                    val pendingCount = todos.size - completedCount

                    val filteredTodos = when (selectedFilter) {
                        TodoFilter.ALL -> todos
                        TodoFilter.COMPLETED -> todos.filter { it.completed }
                        TodoFilter.PENDING -> todos.filter { !it.completed }
                    }

                    Column(modifier = Modifier.fillMaxSize()) {

                        // 🔹 Segmented Button modern
                        SingleChoiceSegmentedButtonRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                        ) {
                            SegmentedButton(
                                selected = selectedFilter == TodoFilter.ALL,
                                onClick = { selectedFilter = TodoFilter.ALL },
                                shape = SegmentedButtonDefaults.itemShape(0, 3),
                                icon = { Icon(Icons.Default.List, contentDescription = null) },
                                label = { Text("All") }
                            )
                            SegmentedButton(
                                selected = selectedFilter == TodoFilter.COMPLETED,
                                onClick = { selectedFilter = TodoFilter.COMPLETED },
                                shape = SegmentedButtonDefaults.itemShape(1, 3),
                                icon = { Icon(Icons.Default.CheckCircle, contentDescription = null) },
                                label = { Text("Completed") }
                            )
                            SegmentedButton(
                                selected = selectedFilter == TodoFilter.PENDING,
                                onClick = { selectedFilter = TodoFilter.PENDING },
                                shape = SegmentedButtonDefaults.itemShape(2, 3),
                                icon = { Icon(Icons.Default.HourglassEmpty, contentDescription = null) },
                                label = { Text("Pending") }
                            )
                        }

                        // 🔹 Statistik ringkas
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                StatItem("All", todos.size, Color(0xFF1976D2))
                                StatItem("Completed", completedCount, Color(0xFF4CAF50))
                                StatItem("Pending", pendingCount, Color(0xFFFF9800))
                            }
                        }

                        Spacer(Modifier.height(8.dp))

                        // 🔹 List Todo
                        if (filteredTodos.isEmpty()) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.HourglassEmpty,
                                    contentDescription = null,
                                    tint = Color.Gray,
                                    modifier = Modifier.size(48.dp)
                                )
                                Spacer(Modifier.height(8.dp))
                                Text("No tasks found", color = Color.Gray)
                            }
                        } else {
                            LazyColumn(
                                contentPadding = PaddingValues(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(filteredTodos) { todo ->
                                    TodoCard(todo.title, todo.userId, todo.completed) {
                                        onItemClick(todo.id)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatItem(label: String, count: Int, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("$count", fontWeight = FontWeight.Bold, color = color)
        Text(label, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
    }
}

@Composable
fun TodoCard(title: String, userId: Int, completed: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .animateContentSize(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .height(48.dp)
                    .background(if (completed) Color(0xFF4CAF50) else Color(0xFFFF9800))
            )
            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))
                Text("User ID: $userId", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }

            AssistChip(
                onClick = {},
                label = { Text(if (completed) "Done" else "Pending") },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = if (completed) Color(0xFF4CAF50) else Color(0xFFFF9800),
                    labelColor = Color.White
                )
            )
        }
    }
}
