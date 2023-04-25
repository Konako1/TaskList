package com.example.tasklistlab.data

data class TaskListUiState(
    /** List of current Tasks */
    val taskList: MutableList<Task> = mutableListOf(),

    var selectedTask: Task = Task()
)