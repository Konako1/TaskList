package com.example.tasklistlab.ui.parts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Shapes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasklistlab.R
import com.example.tasklistlab.data.TaskListUiState
import com.example.tasklistlab.data.TaskUiState
import com.example.tasklistlab.ui.TaskViewModel
import com.example.tasklistlab.ui.theme.Shapes
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun TaskDataScreen(
    viewModel: TaskViewModel,
    taskUiState: TaskUiState,
    modifier: Modifier = Modifier
) {

    var completionDate by rememberSaveable { mutableStateOf(taskUiState.completionDate) }
    var title by rememberSaveable { mutableStateOf(taskUiState.title) }
    var description by rememberSaveable { mutableStateOf(taskUiState.description) }

    Column(
        modifier = modifier.padding(14.dp).fillMaxWidth()
    ) {
        Row {
            Text(
                text = "Название",
                fontSize = 30.sp,
                color = MaterialTheme.colors.onSecondary
            )
            Text(
                text = " *",
                fontSize = 30.sp,
                color = Color.Red
            )
        }
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.None
            ),
            value = title,
            onValueChange = {
                title = it
                viewModel.editTaskData(
                    taskUiState,
                    title = title
                )
            },
            textStyle = TextStyle(
                fontSize = 20.sp,
                color = MaterialTheme.colors.onSecondary
            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    top = 8.dp,
                    bottom = 16.dp
                )
                .background(MaterialTheme.colors.onPrimary)
        )

        Text(
            text = "Описание",
            fontSize = 30.sp,
            color = MaterialTheme.colors.onSecondary
        )
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.None
            ),
            value = description ?: "",
            onValueChange = {
                description = it
                viewModel.editTaskData(
                    taskUiState,
                    description = description
                )
            },
            textStyle = TextStyle(
                fontSize = 20.sp,
                color = MaterialTheme.colors.onSecondary
            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    top = 8.dp,
                    bottom = 16.dp
                )
                .background(MaterialTheme.colors.onPrimary)
        )

        Text(
            text = "Дата Выполнения",
            fontSize = 30.sp,
            color = MaterialTheme.colors.onSecondary
        )

        val dialogState = rememberMaterialDialogState()

        Box(
            modifier = modifier
                .wrapContentWidth()
                .fillMaxWidth()
                .padding(top = 8.dp)
                .border(1.dp, Color(0xFF1C1D1E), RoundedCornerShape(7))
                .clickable {
                    dialogState.show()
                }
                .background(MaterialTheme.colors.onPrimary)
        ) {
            MaterialDialog(
                dialogState = dialogState,
                buttons = {
                    positiveButton("Ок")
                    negativeButton("Отмена")
                }
            ) {
                datepicker(
                    initialDate = completionDate
                ) { date ->
                    completionDate = date
                    viewModel.editTaskData(
                        taskUiState,
                        completionDate = completionDate
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = completionDate.format(DateTimeFormatter.ofPattern("dd MMMM yyy")),
                    color = MaterialTheme.colors.onSecondary,
                    modifier = Modifier
                        .padding(16.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.calendar_image),
                    contentDescription = null,
                    modifier = modifier
                        .padding(top = 16.dp, end = 16.dp)
                        .size(20.dp)
                )
            }
        }
        Row {
            val requiredText = if (taskUiState.title == "") "Заполните обязательные поля!" else ""
            Text(
                text = requiredText,
                color = Color.Red,
                modifier = modifier
                    .padding(top = 16.dp, end = 16.dp)
            )

        }
    }
}