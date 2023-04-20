package com.example.tasklistlab

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.tasklistlab.ui.TaskViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController


@Composable
fun TaskListApp(modifier: Modifier = Modifier, viewModel: TaskViewModel = viewModel()) {
    val navController = rememberNavController()


}