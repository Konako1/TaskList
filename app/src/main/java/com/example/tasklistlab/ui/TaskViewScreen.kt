package com.example.tasklistlab.ui

import androidx.compose.foundation.Image
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasklistlab.R
import com.example.tasklistlab.data.Task
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun TaskViewScreen(
    task: Task,
    modifier: Modifier = Modifier
) {
    var title by rememberSaveable { mutableStateOf(task.title) }
    var description by rememberSaveable { mutableStateOf(task.description) }
    var completionDate by rememberSaveable { mutableStateOf(task.completionDate) }

    Column(
        modifier = modifier.padding(14.dp)
    ) {
        Text(
            text = "Название",
            fontSize = 40.sp,
        )
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.None
            ),
            value = title,
            onValueChange = {},
            textStyle = TextStyle(fontSize = 20.sp),
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    end = 16.dp,
                    top = 16.dp,
                    bottom = 16.dp
                )
        )

        Text(
            text = "Описание",
            fontSize = 40.sp,
        )
        description?.let {
            OutlinedTextField(
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.None
                ),
                value = it,
                onValueChange = {},
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(
                        end = 16.dp,
                        top = 16.dp,
                        bottom = 16.dp
                    )
            )
        }

        Text(
            text = "Дата Выполнения",
            fontSize = 40.sp
        )

        val dialogState = rememberMaterialDialogState()

        Box(
            modifier = modifier
                .wrapContentWidth()
                .fillMaxWidth()
                .padding(top = 16.dp, end = 16.dp)
                .border(1.dp, Color(0xFF1C1D1E), RoundedCornerShape(7))
                .clickable {
                    dialogState.show()
                }
        ) {
            MaterialDialog(
                dialogState = dialogState,
                buttons = {
                    positiveButton("Ок")
                    negativeButton("Отмена")
                }
            ) {
                datepicker { date ->
                    completionDate = date
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text= completionDate.format(DateTimeFormatter.ofPattern("dd MMMM yyy")),
                    color = MaterialTheme.colors.onSurface,
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
    }
}


@Preview
@Composable
fun TaskViewScreenPreview() {
    TaskViewScreen(
        task = Task(
            title = "Какой то тайтл",
            description = "мопвмаорпывмарОПМАорп  проап ро аор аорп аорп ароп аор па орпа орп аорп аро па орп",
            completionDate = LocalDate.now(),
            isCompleted = false
        )
    )
}