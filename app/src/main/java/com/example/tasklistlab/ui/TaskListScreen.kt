package com.example.tasklistlab.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tasklistlab.R
import com.example.tasklistlab.data.Task
import com.example.tasklistlab.data.TaskListUiState
import java.time.LocalDate
import java.time.format.DateTimeFormatter


/**
 * Returns the colors of [Task], where first is background color and second if circle color
 */
val Task.colorPalette: Pair<Color, Color>
    get() {
        var circleColor = Color(0xFF74FA51)
        var bgColor = Color(0xFF1BE00E)
        if (isCompleted) {
            circleColor = Color(0x73D8D8D8)
            bgColor = Color.Gray
        }
        else if (completionDate < LocalDate.now()) {
            circleColor = Color(0xFFFD8787)
            bgColor = Color(0xFFFF4A4A)
        }
        return Pair(bgColor, circleColor)
    }


@Composable
fun TaskListScreen(
    taskList: List<Task>,
    changeSelectedTask: (Task) -> Unit,
    onTaskClicked: () -> Unit,
    onCompletedClicked: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        state = lazyListState,
        modifier = modifier
    ) {
        items(taskList) { task ->
            TaskItemScreen(
                onTaskClicked = {
                    changeSelectedTask(task)
                    onTaskClicked()
                },
                onCompletedClicked = onCompletedClicked,
                task = task
            )
        }
    }
}


@Composable
fun TaskItemScreen(
    onTaskClicked: () -> Unit,
    onCompletedClicked: (Task) -> Unit,
    task: Task,
    modifier: Modifier = Modifier
) {
    val (bgColor, circleColor) = task.colorPalette

    Row(
        modifier = modifier
            .padding(2.dp)
            .height(IntrinsicSize.Max)
            .shadow(1.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .padding(1.dp)
                .fillMaxWidth(0.14f)
                .fillMaxHeight()
                .background(color = bgColor)
                .clickable { onCompletedClicked(task) }
        ) {
            Canvas(
                modifier = modifier.fillMaxSize(0.7f)
            ) {
                drawCircle(color = circleColor )
            }
            if(task.isCompleted) {
                Image(
                    painter = painterResource(id = R.drawable.checkmark),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = Color.Gray),
                    modifier = modifier.width(28.dp)
                )
            }
        }
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxSize()
                .wrapContentHeight()
                .clickable { onTaskClicked() }
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = modifier.padding(8.dp, 8.dp, 8.dp, 0.dp)
                ) {
                    Text(
                        text = task.title,
                        fontSize = 28.sp,
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
                modifier = modifier.padding(8.dp, 8.dp, 8.dp, 0.dp)
            ) {
                task.description?.let { Text(
                    text = it,
                    fontSize = 16.sp,
                    softWrap = true
                ) }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .wrapContentHeight()
                    .height(45.dp)
                    .padding(8.dp, 8.dp, 8.dp, 0.dp)
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.calendar_image),
                    contentDescription = null,
                    modifier = modifier.size(14.dp)
                )
                Spacer(modifier = modifier.size(6.dp))
                Text(
                    text = task.completionDate.format(DateTimeFormatter.ofPattern("dd MMMM y")),
                    fontSize = 14.sp,
                    softWrap = false
                )
            }
        }
    }
}


@Composable
fun TaskItemScreenPreview(viewModel: TaskViewModel = viewModel()) {
    val task = Task(
        title = "Задача",
        description = "Описание задачи dsfsafsafsd asf d f sad fsa df sad f sadf as df as f sa fs asdhgfjasjdf asdgjfhga ajsdhfg",
        completionDate = LocalDate.now().minusDays(0),
        isCompleted = true
    )
    TaskItemScreen(
        onTaskClicked = {},
        onCompletedClicked = {},
        task = task
    )
}


@Preview
@Composable
fun TaskListScreenPreview(viewModel: TaskViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    TaskListScreen(
        taskList = uiState.taskList,
        changeSelectedTask = {},
        onTaskClicked = {},
        onCompletedClicked = {},
    )
}