package com.project.tasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import com.project.tasks.ui.components.Screen
import com.project.tasks.ui.theme.TasksTheme

class MainActivity : ComponentActivity() {

    private val tasksViewModel by viewModels<TasksViewModel>()

    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
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
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun ActivityScreen( viewModel: TasksViewModel ){

    Screen(
        tasks = viewModel.tasks,
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

// compose updates the screen by calling composables again. A process called recomposition.

// LocalContentColor gives you the preferred color for content such as Icons and Typography.
// It is changed by composables such as Surface that draw a background...

// Recomposition is the process of calling composables again with new inputs to update the compose tree. In this case when TodoScreen is called again with a new list,
// LazyColumn will recompose all of the children on the screen. This will then call TodoRow again, generating a new random tint.

// Recomposing a composable should be side-effect free.

// remember gives a composable function memory.
// A value computed by remember will be stored in the composition tree, and only be recomputed if the keys to remember change.

// A remember call has two parts:
//
//key arguments – the "key" that this remember uses, this is the part that is passed in parenthesis. Here we're passing todo.id as the key.
//calculation – a lambda that computes a new value to be remembered, passed in a trailing lambda. Here we're computing a random value with randomTint().

// for same id, calculation fn won't be called during recomposition...

// Values remembered in composition are forgotten as soon as their calling composable is removed from the tree.
//
//They will also be re-initialized if the calling composable moves in the tree. You can cause this in the LazyColumn by removing items at the top.

// An idempotent composable always produces the same result for the same inputs and has no side-effects on recomposition.
//
//Composables should be idempotent to support recomposition.

// When adding memory to a composable, always ask yourself "will some caller reasonably want to control this?"
//If yes, make a parameter instead | no, keep it as a local variable.

//remember stores values in the Composition, and will forget them if the composable that called remember is removed.
// This means you shouldn't rely upon remember to store important things inside of composables that add and remove children such as LazyColumn.


// Built-in composables are designed for unidirectional data flow

//TextField is the compose equivalent to Material's EditText

// MutableState<String> which is a built-in type of Compose that provides an observable state holder.

// mutableStateOf creates a MutableState<T> which is an observable state holder built into compose.
//      interface MutableState<T> : State<T> { override var value: T }

// Any changes to *value* will automatically recompose any composable functions that read this state.

// 3 ways to create...

//val state = remember { mutableStateOf(default) }
//var value by remember { mutableStateOf(default) }
//val (value, setValue) = remember { mutableStateOf(default) }

// When creating State<T> (or other stateful objects) in composition, it's important to remember it. Otherwise it will be re-initialized every composition.

// MutableState<T> similar to MutableLiveData<T>, but integrated with the compose runtime.
// Since it's observable, it will tell compose whenever it's updated so compose can recompose any composables that read it

// Recomposition can change the structure of the composition tree based on new data.

// To handle work with the keyboard, TextField provides two parameters:
//
//keyboardOptions - used to enable showing the Done IME action
//keyboardActions - used to specify the action to be triggered in response to specific IME actions triggered - in our case, once Done is pressed, we want submit to be called and the keyboard to be hidden.

// mutableStateListOf allows us to create an instance of MutableList that is observable.
// This means that we can work with todoItems in the same way we work with a MutableList, removing the overhead of working with LiveData<List>.

// The work done with mutableStateListOf and MutableState is intended for Compose.
