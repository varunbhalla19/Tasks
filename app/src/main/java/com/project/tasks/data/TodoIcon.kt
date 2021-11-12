package com.project.tasks.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

enum class TodoIcon(val imageVector: ImageVector, val description: String) {

    Square(Icons.Default.CropSquare, "SquareIcon"),
    Done(Icons.Default.Done, "DoneIcon"),
    Event(Icons.Default.Event, "EventIcon"),
    Privacy(Icons.Default.PrivacyTip, "PrivacyIcon"),
    Trash(Icons.Default.RestoreFromTrash, "TrashIcon");

    companion object {
        val Default = Square
    }
}