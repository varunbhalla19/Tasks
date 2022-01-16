package com.project.tasks.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.tasks.data.TaskItem
import com.project.tasks.data.TodoIcon
import com.project.tasks.ui.theme.TasksTheme


@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun Screen(
    tasks: List<TaskItem>,
    currentlyEditing: TaskItem?,
    onAddItem: (TaskItem) -> Unit = {},
    onRemove: (TaskItem) -> Unit = {},
    onStartEdit: (TaskItem) -> Unit,
    onEditItemChange: (TaskItem) -> Unit,
    onEditDone: () -> Unit
){
    Column {

        LazyColumn(
            Modifier
                .weight(1f)
                .fillMaxWidth()
        ){
            this.items(items = tasks){ item ->
                if(currentlyEditing?.id == item.id){
                    TodoInlineEdit(
                        item = currentlyEditing,
                        onEditItemChange = onEditItemChange,
                        onEditDone = onEditDone,
                        onRemove = {onRemove(item)}
                    )
                }else {
                    TaskRow( task = item, onClick = { onStartEdit(item) } )
                }
            }
        }

        TodoItemInputBackground(
            elevate = true,
            modifier = Modifier.fillMaxWidth()
        ) {
            if( currentlyEditing == null ){
                TodoInputRow(onItemComplete = onAddItem)
            }
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun TodoInlineEdit(
    item: TaskItem,
    onEditItemChange: (TaskItem) -> Unit,
    onEditDone: () -> Unit,
    onRemove: () -> Unit
){
    TodoItemInput(
        text = item.task,
        setText = { onEditItemChange(item.copy(task = it)) },
        icon = item.icon,
        setIcon = { onEditItemChange(item.copy(icon = it)) },
        enabled = true,
        submit = onEditDone
    ){
        Row {
            val shrinkButtons = Modifier.widthIn(20.dp)
            TextButton(onClick = onEditDone, modifier = shrinkButtons) {
                Text(
                    text = "\uD83D\uDCBE",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(30.dp)
                )
            }
            TextButton(onClick = onRemove, modifier = shrinkButtons) {
                Text(
                    text = "❌",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(30.dp)
                )
            }
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
        modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(task.task)
        Icon(
            imageVector = task.icon.imageVector,
            contentDescription = task.icon.description,
        )
    }
}

@Composable
fun TodoItemInputBackground(
    elevate: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    val animatedElevation by animateDpAsState(if (elevate) 1.dp else 0.dp, TweenSpec(500))
    Surface(
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.05f),
        elevation = animatedElevation,
        shape = RectangleShape,
    ) {
        Row(
            modifier = modifier.animateContentSize(animationSpec = TweenSpec(300)),
            content = content
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun AnimatedIconsRow(
    icon: TodoIcon,
    setIcon: (TodoIcon) -> Unit,
    modifier: Modifier = Modifier,
    visible: Boolean = true
){
    val enter = remember { fadeIn(animationSpec = TweenSpec(300, easing = FastOutLinearInEasing)) }
    val exit = remember { fadeOut(animationSpec = TweenSpec(100, easing = FastOutSlowInEasing)) }
    
    Box(modifier = modifier.defaultMinSize(minHeight = 16.dp)){
        AnimatedVisibility(
            visible = visible,
            enter = enter,
            exit = exit
        ) {
            IconsRow(icon = icon, onIconChange = setIcon)
        }
    }
}

@Composable
fun IconsRow(
    icon: TodoIcon,
    onIconChange: (TodoIcon) -> Unit,
    modifier: Modifier = Modifier
){
    Row(modifier){
        TodoIcon.values().forEach {
            SelectableIconButton(
                icon = it.imageVector,
                description = it.description,
                onIconSelected = { onIconChange(it) },
                isSelected = it == icon
            )
        }
    }
}

@Composable
fun SelectableIconButton(
    icon: ImageVector,
    description: String,
    onIconSelected: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier
){
    val tint = if(isSelected){
        MaterialTheme.colors.primary
    } else {
        MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
    }
    TextButton(
        onClick = onIconSelected,
        shape = CircleShape,
        modifier = modifier
    ) {
        Column {
            Icon(
                imageVector = icon,
                tint = tint,
                contentDescription = description
            )
            if (isSelected) {
                Box(
                    Modifier
                        .padding(top = 3.dp)
                        .width(icon.defaultWidth)
                        .height(1.dp)
                        .background(tint)
                )
            } else {
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
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
//            Screen(items)
        }
    }
}