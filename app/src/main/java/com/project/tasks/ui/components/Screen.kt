package com.project.tasks.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.tasks.data.TaskItem
import com.project.tasks.data.TodoIcon
import com.project.tasks.ui.theme.TasksTheme


@Composable
fun Screen(
    tasks: List<TaskItem>,
    modifier: Modifier = Modifier
){
    Column() {
        
        LazyColumn(
            Modifier.weight(1f).fillMaxWidth()
        ){
            this.items(items = tasks){
                item -> TaskRow(task = item, onClick = {})
            }
        }
        
        Button(onClick = {}, modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth() ) {

            Text(text = "Add Item")

        }
        
    }
}

@Composable
fun TaskRow(
    task: TaskItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        Modifier
            .clickable { }
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(task.task)
        Icon(
            task.icon.imageVector,
            task.icon.description
        )
    }
}


@Preview
@Composable
fun PreviewTodoScreen() {
    val items = listOf(
        TaskItem("Learn compose", TodoIcon.Event),
        TaskItem("Take the codelab"),
        TaskItem("Apply state", TodoIcon.Done),
        TaskItem("Build dynamic UIs", TodoIcon.Square)
    )
    TasksTheme {
        Surface{
            Screen(items)
        }
    }
}