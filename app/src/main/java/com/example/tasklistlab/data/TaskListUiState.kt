package com.example.tasklistlab.data

data class TaskListUiState(
    /** List of current Tasks */
    val taskList: MutableList<TaskUiState> = mutableListOf(),

    var currentTask: TaskUiState = TaskUiState()
)