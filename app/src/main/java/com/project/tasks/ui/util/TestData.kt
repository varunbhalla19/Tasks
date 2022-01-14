package com.project.tasks.ui.util

import com.project.tasks.data.TaskItem
import com.project.tasks.data.TodoIcon

fun generateRandomTodoItem(): TaskItem {
    val message = listOf(
        "Learn compose",
        "Learn state",
        "Build dynamic UIs",
        "Learn Unidirectional Data Flow",
        "Integrate LiveData",
        "Integrate ViewModel",
        "Remember to savedState!",
        "Build stateless composables",
        "Use state from stateless composables"
    ).random()
    val icon = TodoIcon.values().random()
    return TaskItem(message, icon)
}