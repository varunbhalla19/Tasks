package com.project.tasks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.project.tasks.data.TaskItem

class TasksViewModel: ViewModel() {

    // private state
    private var currentEditPosition by mutableStateOf(-1)

    var tasks = mutableStateListOf<TaskItem>()
    private set

    fun addTask(task: TaskItem){
        tasks.add(task)
    }

    fun removeTask(task: TaskItem){
        tasks.remove(task)
        onEditDone()
    }

    val currentEditItem: TaskItem?
    get() = tasks.getOrNull(currentEditPosition)

    fun onEditItemSelected(item: TaskItem) {
        currentEditPosition = tasks.indexOf(item)
    }

    fun onEditDone() {
        currentEditPosition = -1
    }

    fun onEditItemChange(item: TaskItem) {
        val currentItem = requireNotNull(currentEditItem)
        require(currentItem.id == item.id) {
            "You can only change an item with the same id as currentEditItem"
        }
        tasks[currentEditPosition] = item
    }

}