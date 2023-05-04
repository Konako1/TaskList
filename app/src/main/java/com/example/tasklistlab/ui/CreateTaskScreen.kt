package com.example.tasklistlab.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tasklistlab.data.TaskUiState
import com.example.tasklistlab.ui.parts.TaskDataScreen
import java.time.LocalDate


@Composable
fun CreateTaskScreen(
    viewModel: TaskViewModel,
    taskUiState: TaskUiState,
    onCreateClick: (TaskUiState) -> Unit,
    onCancelClick: (TaskUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        TaskDataScreen(
            viewModel = viewModel,
            taskUiState = taskUiState
        )
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp, 8.dp, 20.dp, 8.dp)
        ) {
            Button(
                onClick = { onCancelClick(taskUiState) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Gray,
                    contentColor = MaterialTheme.colors.onSecondary
                ),
                modifier = modifier.width(140.dp)
            ) {
                Text(text = "Отмена")
            }
            if (taskUiState.title != "") {
                Button(
                    onClick = { onCreateClick(taskUiState) },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = MaterialTheme.colors.onSecondary
                    ),
                    modifier = modifier.width(140.dp)
                ) {
                    Text(text = "Создать")
                }
            }
            else {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Gray,
                        contentColor = MaterialTheme.colors.onSecondary
                    ),
                    modifier = modifier.width(140.dp)
                ) {
                    Text(text = "Создать")
                }
            }
        }
    }
}


@Preview
@Composable
fun CreateTaskScreenPreview(viewModel: TaskViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    CreateTaskScreen(
        viewModel = viewModel,
        taskUiState = uiState.currentTask,
        onCreateClick = {},
        onCancelClick = {}
    )
}