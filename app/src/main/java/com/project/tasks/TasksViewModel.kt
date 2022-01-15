package com.project.tasks

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.tasks.data.TaskItem

class TasksViewModel: ViewModel() {

    var tasks = mutableStateListOf<TaskItem>()
    private set

    fun addTask(task: TaskItem){
        tasks.add(task)
    }

    fun removeTask(task: TaskItem){
        tasks.remove(task)
    }

}