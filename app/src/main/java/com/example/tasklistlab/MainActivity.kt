package com.example.tasklistlab

import androidx.activity.compose.setContent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.tasklistlab.ui.theme.TaskListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskListTheme {
                TaskListApp()
            }
        }
    }
}