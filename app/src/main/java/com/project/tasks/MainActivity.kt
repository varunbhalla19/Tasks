package com.project.tasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import com.project.tasks.data.TaskItem
import com.project.tasks.ui.components.Screen
import com.project.tasks.ui.theme.TasksTheme

class MainActivity : ComponentActivity() {

    val tasksViewModel by viewModels<TasksViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TasksTheme {
                Surface {
                    ActivityScreen(viewModel = tasksViewModel)
                }
            }
        }
    }
}

// A bridge between the state stored in our ViewModel and the Screen composable.
@Composable
fun ActivityScreen( viewModel: TasksViewModel ){

    val tasks by viewModel.tasks.observeAsState(listOf())

    Screen(
        tasks = tasks,
        onAddItem = { viewModel.addTask(it) },
        onRemove = { viewModel.removeTask(it) }
    )
}

//A ViewModel lets you extract state from your UI and define events that the UI can call to update that state.
// LiveData holds state which is observed by the UI...A LiveData is an observable state holder, which means that it provides a way for anyone to observe changes to the state.

// LiveData, StateFlow, Flow, and Observable are all observable.

// Unidirectional data flow is a design where events flow up and state flows down.
//For example, in a ViewModel events are passed up with method calls from the UI while state flows down using LiveData.
//Any design where events flow up and state goes down is unidirectional.

//A stateless composable is a composable that cannot directly change any state.

//  if it's stateless how can it display an editable list? It does that by using a technique called state hoisting.
//  State hoisting is the pattern of moving state up to make a component stateless.
//  Stateless components are easier to test, tend to have fewer bugs, and open up more opportunities for reuse.

// State hoisting is a pattern of moving state up to make a component stateless.

// When applied to composables, this often means introducing two parameters to the composable.
//
//value: T – the current value to display
//onValueChange: (T) -> Unit – an event that requests the value to change, where T is the proposed new value.

// use ViewModel to hoist the state from Screen.

// onAddItem = { viewModel.addTask(it) }, OR onAddItem = viewModel::addTask,

// val tasks by viewModel.tasks.observeAsState(listOf()) => observe the LiveData and let us use the current value of list directly

// *by* is the property delegate syntax in Kotlin, it lets us automatically unwrap the State<List<TodoItem>> from observeAsState into a regular List<TodoItem>.

// .observeAsState observes a LiveData<T> and converts it into a State<T> object so Compose can react to value changes...

// observeAsState observes a LiveData and returns a State object that is updated whenever the LiveData changes.
// It will automatically stop observing when the composable is removed from composition.

// listOf() is an initial value to avoid possible null results before the LiveData is initialized, if it wasn't passed items would be List<TodoItem>? which is nullable.

// compose updates the screen by calling composables again. A process called recomposition...

// A stateful composable is a composable that owns a piece of state that it can change over time...

