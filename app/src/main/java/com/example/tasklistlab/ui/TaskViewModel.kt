package com.example.tasklistlab.ui

import androidx.lifecycle.ViewModel
import com.example.tasklistlab.data.DataSource
import com.example.tasklistlab.data.TaskUiState
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

    private val _taskUiState = MutableStateFlow(TaskUiState())
    val taskUiState: StateFlow<TaskUiState> = _taskUiState.asStateFlow()


    fun updateTaskList() {
        val taskList = sortTasks(_uiState.value.taskList.toMutableList())
        _uiState.update { currentState ->
            currentState.copy(
                taskList = taskList,
                currentTask = TaskUiState()
            )
        }
    }


    private fun sortTasks(taskUiStateList: MutableList<TaskUiState>): MutableList<TaskUiState> {
        var sorted = true
        // bubble sort
        while (sorted) {
            sorted = false
            for (i in 0..taskUiStateList.size - 2) {
                if (taskUiStateList[i].completionDate > taskUiStateList[i + 1].completionDate) {
                    val swap = taskUiStateList[i]
                    val newTaskUiState = TaskUiState(
                        title = swap.title,
                        description = swap.description,
                        completionDate = swap.completionDate,
                        isCompleted = swap.isCompleted
                    )
                    taskUiStateList[i] = taskUiStateList[i + 1]
                    taskUiStateList[i + 1] = newTaskUiState
                    sorted = true
                }
            }
        }

        var sortedTaskListUiState: MutableList<TaskUiState> = taskUiStateList.toMutableList()
        var completedTaskListUiState: MutableList<TaskUiState> = mutableListOf()

        // completed and non completed task sort (completed going to the end of list)
        for (task in taskUiStateList) {
            if (task.isCompleted) {
                completedTaskListUiState.add(task)
                sortedTaskListUiState.remove(task)
            }
        }

        sortedTaskListUiState.addAll(completedTaskListUiState)
        return sortedTaskListUiState
    }

    fun addTaskToList(task: TaskUiState) {
        _uiState.update { currentState ->
            currentState.taskList.add(TaskUiState(
                title = task.title,
                description = task.description,
                completionDate = task.completionDate
            ))
            currentState.copy(
                taskList = currentState.taskList
            )
        }
    }

    fun changeTaskCompletedValue(taskUiState: TaskUiState) {
        _uiState.update { currentState ->
            val index = currentState.taskList.indexOf(taskUiState)
            currentState.taskList[index].isCompleted = !taskUiState.isCompleted
            currentState.copy(
                taskList = currentState.taskList.toMutableList()
            )
        }

    }

    fun changeSelectedTask(taskUiState: TaskUiState) {
        _uiState.update {
            it.copy(
                currentTask = taskUiState
            )
        }
    }

    fun deleteTask(taskUiState: TaskUiState) {
        _uiState.update { currentState ->
            currentState.taskList.remove(taskUiState)
            currentState.copy(
                taskList = currentState.taskList
            )
        }
    }

    fun editTaskData(
        task: TaskUiState,
        title: String? = null,
        description: String? = null,
        completionDate: LocalDate? = null,
        isCompleted: Boolean? = null
    )
    {
        val index = _uiState.value.taskList.indexOf(task)
        if (index == -1) {
            _uiState.update { currentState ->
                val newTask = TaskUiState(
                    title = title ?: task.title,
                    description = description ?: task.description,
                    completionDate = completionDate ?: task.completionDate,
                    isCompleted = isCompleted ?: false
                )
                currentState.copy(
                    currentTask = newTask
                )
            }
            return
        }
        _uiState.update { currentState ->
            val newTask = TaskUiState(
                title = title ?: task.title,
                description = description ?: task.description,
                completionDate = completionDate ?: task.completionDate,
                isCompleted = isCompleted ?: task.isCompleted
            )
            currentState.taskList[index] = newTask
            currentState.copy(
                currentTask = newTask
            )
        }
    }

    private fun preGenTasks(): MutableList<TaskUiState> {
        val taskUiStateList: MutableList<TaskUiState> = mutableListOf()
        for (task in DataSource.tasks) {
            taskUiStateList.add(
                TaskUiState(
                    title = task.first,
                    description = task.second,
                    completionDate = LocalDate.now().plusDays(Random.nextLong(-5, 5)),
                    isCompleted = Random.nextBoolean()
            ))
        }

        return sortTasks(taskUiStateList)
    }
}