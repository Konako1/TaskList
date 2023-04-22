package com.example.tasklistlab.ui

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.tasklistlab.data.DataSource
import com.example.tasklistlab.data.Task
import com.example.tasklistlab.data.TaskListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import kotlin.random.Random

class TaskViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TaskListUiState(taskList = preGenTasks()))
    val uiState: StateFlow<TaskListUiState> = _uiState.asStateFlow()

    fun getSortedTasks(): List<Task> {
        val taskList = _uiState.value.taskList
        var sorted = true
        // bubble sort
        while (sorted) {
            sorted = false
            for (i in taskList.indices - 2) {
                if (taskList[i].completionDate < taskList[i + 1].completionDate) {
                    val swap = taskList[i]
                    taskList[i] = taskList[i + 1]
                    taskList[i + 1] = swap
                    sorted = true
                }
            }
        }

        var sortedTaskList: MutableList<Task> = taskList
        var completedTaskList: MutableList<Task> = mutableListOf()
        var removed = 0
        // completed and non completed task sort (completed going to the end of list)
        for ((i, task) in taskList.withIndex()) {
            if (task.isCompleted) {
                completedTaskList.add(task)
                sortedTaskList.removeAt(i - removed)
                removed++
            }
        }

        sortedTaskList.addAll(completedTaskList)
        return sortedTaskList
    }

    fun addTaskToList(title: String, description: String?, completionDate: LocalDate) {
        _uiState.value.taskList.add(Task(
            title = title,
            description = description,
            completionDate = completionDate,
        ))
    }

    fun markTaskAsCompleted(taskId: Int) {
        _uiState.value.taskList[taskId].isCompleted = true
    }

    fun deleteTask(taskId: Int) {
        _uiState.value.taskList.removeAt(taskId)
    }

    /**
     * Returns the colors of [Task], where first is background color and second if circle color
     */
    fun getColorsPalette(task: Task): Pair<Color, Color> {
        var circleColor = Color(0x73C1F0B5)
        var bgColor = Color(0x7357E031)
        if (task.isCompleted) {
            circleColor = Color(0x73D8D8D8)
            bgColor = Color.Gray
        }
        else if (task.completionDate < LocalDate.now()) {
            circleColor = Color(0x9EFF8B8B)
            bgColor = Color(0x9EFF4747)
        }
        return Pair(bgColor, circleColor)
    }

    private fun preGenTasks(): MutableList<Task> {
        val taskList: MutableList<Task> = mutableListOf()
        for (task in DataSource.tasks) {
            taskList.add(
                Task(
                    title = task.first,
                    description = task.second,
                    completionDate = LocalDate.now().plusDays(Random.nextLong(1, 10))
            ))
        }
        return taskList
    }
}