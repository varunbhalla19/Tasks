package com.project.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.tasks.data.TaskItem

class TasksViewModel: ViewModel() {

    private var _tasks= MutableLiveData(mutableListOf<TaskItem>())
    val tasks: LiveData<MutableList<TaskItem>> = _tasks


    fun addTask(task: TaskItem){
        _tasks.value?.add(task)
    }

    fun removeTask(task: TaskItem){
        _tasks.value?.remove(task)
    }

}