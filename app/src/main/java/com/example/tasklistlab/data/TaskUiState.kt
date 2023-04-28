package com.example.tasklistlab.data

import java.time.LocalDate

data class TaskUiState(
    var title: String = "",

    var description: String? = null,

    /** Date when task should be completed */
    var completionDate: LocalDate = LocalDate.now(),

    var isCompleted: Boolean = false
)
