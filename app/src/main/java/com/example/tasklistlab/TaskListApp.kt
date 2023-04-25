package com.example.tasklistlab

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tasklistlab.ui.TaskViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tasklistlab.ui.CreateTaskScreen
import com.example.tasklistlab.ui.TaskListScreen
import com.example.tasklistlab.ui.TaskViewScreen


enum class TaskListRoutes(@StringRes val title: Int) {
    Tasks(title = R.string.app_name),
    Create(title = R.string.create_task),
    Overview(title = R.string.view_task)
}


@Composable
fun TopBar(
    currentScreen: TaskListRoutes,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    canCreateTask: Boolean,
    navigateToCreateTask: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = currentScreen.title)) },
        actions = {
            IconButton(onClick = navigateToCreateTask) {
                if (canCreateTask) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null,
                        modifier = modifier.size(30.dp)
                    )
                }
            }
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button)
                    )
                }
            }
        }
    )
}


@Composable
fun TaskListApp(modifier: Modifier = Modifier, viewModel: TaskViewModel = viewModel()) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = TaskListRoutes.valueOf(
        backStackEntry?.destination?.route ?: TaskListRoutes.Tasks.name
    )

    Scaffold(
        topBar = {
            TopBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = {
                        navController.navigateUp()
                },
                canCreateTask = navController.previousBackStackEntry == null,
                navigateToCreateTask = {
                    navController.navigate(TaskListRoutes.Create.name)
                }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = TaskListRoutes.Tasks.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = TaskListRoutes.Tasks.name) {
                TaskListScreen(
                    taskList = uiState.taskList,
                    changeSelectedTask = { task ->
                        viewModel.changeSelectedTask(task)
                    },
                    onTaskClicked = {
                        navController.navigate(
                            route = TaskListRoutes.Overview.name
                        )
                    },
                    onCompletedClicked = { task ->
                        viewModel.changeTaskCompletedValue(task)
                        viewModel.updateTaskList()
                    }
                )
            }
            composable(route = TaskListRoutes.Create.name) {
                CreateTaskScreen(

                )
            }
            composable(route = TaskListRoutes.Overview.name) {
                TaskViewScreen(
                    task = uiState.selectedTask
                )
            }
        }
    }
}


private fun cancelAndReturnToMainScreen(
    navController: NavHostController
) {
    navController.popBackStack(TaskListRoutes.Tasks.name, inclusive = false)
}