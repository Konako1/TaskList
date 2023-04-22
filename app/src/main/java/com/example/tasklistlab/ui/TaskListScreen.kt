package com.example.tasklistlab.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasklistlab.R
import com.example.tasklistlab.data.Task
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun TaskListScreen(
    getColorsPalette: (Task) -> Pair<Color, Color>,
    tasks: List<Task>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(tasks.count()) { i ->
            TaskItemScreen(
                getColorsPalette = { getColorsPalette(tasks[i]) },
                task = tasks[i]
            )
        }
    }
}


@Composable
fun TaskItemScreen(
    getColorsPalette: (Task) -> Pair<Color, Color>,
    task: Task,
    modifier: Modifier = Modifier
) {
    val colors = getColorsPalette(task)
    val bgColor = colors.first
    val circleColor = colors.second

    Row(modifier = modifier
        .height(IntrinsicSize.Max)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .width(30.dp)
                .fillMaxHeight()
                .background(color = bgColor)
        ) {
            Canvas(
                modifier = modifier.size(20.dp)
            ) {
                drawCircle(color = circleColor )
            }
            if(task.isCompleted) {
                Image(
                    painter = painterResource(id = R.drawable.checkmark),
                    contentDescription = null,
                    modifier = modifier.size(16.dp),
                    colorFilter = ColorFilter.tint(color = Color.Gray)
                )
            }
        }
        Column(
            modifier = modifier.width(150.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = modifier.padding(8.dp)
                ) {
                    Text(
                        text = task.title,
                        fontSize = 14.sp,
                    )
                }
                Column(
                    modifier = modifier.padding(8.dp)
                ) {
                    Row() {
                        Image(
                            painter = painterResource(id = R.drawable.delete_image),
                            contentDescription = null,
                            modifier = modifier.size(8.dp)
                        )
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
                modifier = modifier.padding(8.dp, 0.dp, 8.dp, 8.dp)
            ) {
                task.description?.let { Text(
                    text = it,
                    fontSize = 6.sp,
                    softWrap = true
                ) }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.padding(8.dp, 0.dp, 8.dp, 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.calendar_image),
                    contentDescription = null,
                    modifier = modifier.size(6.dp)
                )
                Spacer(modifier = modifier.size(2.dp))
                Text(
                    text = task.completionDate.format(DateTimeFormatter.ofPattern("d.M.y")),
                    fontSize = 6.sp,
                    softWrap = false
                )
            }
        }
    }
}


@Preview
@Composable
fun TaskItemScreenPreview(viewModel: TaskViewModel = viewModel()) {
    val task = Task(
        title = "Задача",
        description = "Описание задачи dsfsafsafsd asdhgfjasjdf asdgjfhga ajsdhfg",
        completionDate = LocalDate.now().minusDays(0),
        isCompleted = false
    )
    TaskItemScreen(
        getColorsPalette = { viewModel.getColorsPalette(task) },
        task = task
    )
}