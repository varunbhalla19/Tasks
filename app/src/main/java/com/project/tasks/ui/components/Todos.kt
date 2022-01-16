package com.project.tasks.ui.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.tasks.data.TaskItem
import com.project.tasks.data.TodoIcon

@ExperimentalComposeUiApi
@Composable
fun TodoInputText(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier,
    onImeAction: () -> Unit = {}
){

    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = text,
        onValueChange = onTextChange,
        maxLines = 1,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            onImeAction()
            keyboardController?.hide()
        }),
        modifier = modifier
    )
}

@Composable
fun TodoEditButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
){
    TextButton(
        onClick = onClick,
        shape = CircleShape,
        enabled = enabled,
        modifier = modifier
    ) {
        Text(text)
    }
}

@ExperimentalComposeUiApi
@Composable
fun TodoInputTextField(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier,
    onImeAction: () -> Unit = {}
) {
    TodoInputText(text, onTextChange, modifier, onImeAction)
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun TodoInputRow(
    onItemComplete: (TaskItem) -> Unit
){
    val (text, setText) = remember { mutableStateOf("") }
    val (icon, setIcon) = remember { mutableStateOf(TodoIcon.Default) }
    val enabled = text.isNotBlank()
    val submit = {
        onItemComplete(TaskItem(text, icon))
        setText("")
    }
    TodoItemInput(text = text, setText = setText, icon = icon, setIcon = setIcon, enabled = enabled, submit = submit){
        TodoEditButton(
            onClick = submit,
            text = "Add",
            enabled = text.isNotBlank()
        )
    }
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun TodoItemInput(
    text: String,
    setText: (String) -> Unit,
    icon: TodoIcon,
    setIcon: (TodoIcon) -> Unit,
    enabled: Boolean,
    submit: () -> Unit,
    buttonSlot: @Composable() () -> Unit
) {

    Column() {

        Row(
            Modifier
                .padding(horizontal = 16.dp)
                .padding(vertical = 16.dp)
        ) {

            TodoInputTextField(
                text = text,
                onTextChange = setText,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                onImeAction = submit
            )

            Spacer(modifier = Modifier.width(8.dp))

            Box(Modifier.align(Alignment.CenterVertically)) {
                buttonSlot()
            }
        }

        if( enabled ){
            AnimatedIconsRow(
                icon, setIcon, Modifier.padding(top = 8.dp), enabled
            )
        } else {
//            Spacer(modifier = Modifier.height(24.dp))
        }
    }

}