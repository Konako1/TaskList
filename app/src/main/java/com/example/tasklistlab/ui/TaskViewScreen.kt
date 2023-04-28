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
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tasklistlab.R
import com.example.tasklistlab.data.TaskUiState
import com.example.tasklistlab.ui.parts.TaskDataScreen
import java.time.LocalDate

@Composable
fun TaskViewScreen(
    taskUiState: TaskUiState,
    viewModel: TaskViewModel,
    onDeleteClick: (TaskUiState) -> Unit,
    onCompletedClick: (TaskUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(

    ) {
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
                onClick = { onDeleteClick(taskUiState) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.error,
                    contentColor = MaterialTheme.colors.onPrimary
                ),
                modifier = modifier.width(140.dp)
            ) {
                Text(text = "Удалить")
            }
            Button(
                onClick = { onCompletedClick(taskUiState) },
                colors = if (taskUiState.isCompleted)
                    ButtonDefaults.buttonColors(
                        backgroundColor = Color.Gray,
                        contentColor = MaterialTheme.colors.onPrimary
                ) else
                    ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF2EAD20),
                        contentColor = MaterialTheme.colors.onPrimary
                ),
                modifier = modifier.width(140.dp)
            ) {
                Text(
                    text = if (taskUiState.isCompleted) "Отслеживать" else "Выполнить"
                )
            }
        }
    }
}


@Preview
@Composable
fun TaskViewScreenPreview(viewModel: TaskViewModel = viewModel()) {
    TaskViewScreen(
        viewModel = viewModel,
        taskUiState = TaskUiState(
            title = "Какой то тайтл",
            description = "мопвмаорпывмарОПМАорп  проап ро аор аорп аорп ароп аор па орпа орп аорп аро па орп",
            completionDate = LocalDate.now(),
            isCompleted = false
        ),
        onCompletedClick = {},
        onDeleteClick = {}
    )
}