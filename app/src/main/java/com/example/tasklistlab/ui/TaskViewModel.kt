package com.example.tasklistlab.ui

import androidx.compose.runtime.currentComposer
import androidx.lifecycle.ViewModel
import com.example.tasklistlab.data.DataSource
import com.example.tasklistlab.data.Task
import com.example.tasklistlab.data.TaskListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import kotlin.random.Random

class TaskViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TaskListUiState(taskList = preGenTasks()))
    val uiState: StateFlow<TaskListUiState> = _uiState.asStateFlow()


    fun updateTaskList() {
        val taskList = sortTasks(_uiState.value.taskList.toMutableList())
        _uiState.update { currentState ->
            currentState.copy(
                taskList = taskList
            )
        }
    }


    private fun sortTasks(taskList: MutableList<Task>): MutableList<Task> {
        var sorted = true
        // bubble sort
        while (sorted) {
            sorted = false
            for (i in 0..taskList.size - 2) {
                if (taskList[i].completionDate > taskList[i + 1].completionDate) {
                    val swap = taskList[i]
                    val newTask = Task(
                        title = swap.title,
                        description = swap.description,
                        completionDate = swap.completionDate,
                        isCompleted = swap.isCompleted
                    )
                    taskList[i] = taskList[i + 1]
                    taskList[i + 1] = newTask
                    sorted = true
                }
            }
        }

        var sortedTaskList: MutableList<Task> = taskList.toMutableList()
        var completedTaskList: MutableList<Task> = mutableListOf()

        // completed and non completed task sort (completed going to the end of list)
        for (task in taskList) {
            if (task.isCompleted) {
                completedTaskList.add(task)
                sortedTaskList.remove(task)
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

    fun changeTaskCompletedValue(task: Task) {
        _uiState.update { currentState ->
            val index = currentState.taskList.indexOf(task)
            currentState.taskList[index].isCompleted = !task.isCompleted
            currentState.copy(
                taskList = currentState.taskList.toMutableList()
            )
        }

    }

    fun changeSelectedTask(task: Task) {
        _uiState.update {
            it.copy(
                selectedTask = task
            )
        }
    }

    fun deleteTask(task: Task) {
        _uiState.value.taskList.remove(task)
    }

    private fun preGenTasks(): MutableList<Task> {
        val taskList: MutableList<Task> = mutableListOf()
        for (task in DataSource.tasks) {
            taskList.add(
                Task(
                    title = task.first,
                    description = task.second,
                    completionDate = LocalDate.now().plusDays(Random.nextLong(-5, 5)),
                    isCompleted = Random.nextBoolean()
            ))
        }

        return sortTasks(taskList)
    }
}