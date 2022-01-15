package com.project.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.tasks.data.TaskItem

class TasksViewModel: ViewModel() {

    private var _tasks= MutableLiveData(listOf<TaskItem>())
    val tasks: LiveData<List<TaskItem>> = _tasks


    fun addTask(task: TaskItem){
        _tasks.value = _tasks.value!!.plus(listOf(task))
    }

    fun removeTask(task: TaskItem){
        _tasks.value = _tasks.value!!.toMutableList().also {
            it.remove(task)
        }
    }

}