package com.example.tasklistlab

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.tasklistlab.ui.TaskViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tasklistlab.ui.CreateTaskScreen
import com.example.tasklistlab.ui.TaskListScreen
import com.example.tasklistlab.ui.TaskViewScreen


enum class TaskListAppScreen() {
    Tasks,
    Create,
    Overview
}


@Composable
fun TopBar(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back_button)
                )
            }
        }
    )
}


@Composable
fun TaskListApp(modifier: Modifier = Modifier, viewModel: TaskViewModel = viewModel()) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopBar(
                navigateUp = {  }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = TaskListAppScreen.Tasks.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = TaskListAppScreen.Tasks.name) {
                TaskListScreen(
                    getColorsPalette = { task -> viewModel.getColorsPalette(task) },
                    tasks = viewModel.getSortedTasks()
                )
            }
            composable(route = TaskListAppScreen.Create.name) {
                CreateTaskScreen()
            }
            composable(route = TaskListAppScreen.Overview.name) {
                //TaskViewScreen(task )
            }
        }
    }
}