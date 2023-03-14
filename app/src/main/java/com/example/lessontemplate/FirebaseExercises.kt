package com.example.lessontemplate

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*

@Composable
fun FirstExercise() {
    var username by remember { mutableStateOf("") }

    Column() {
        OutlinedTextField(value = username, onValueChange = { x -> username = x } )
        Button(onClick = { AddUser(username)  }) {
            Text("Add user")
        }
    }
}

fun AddUser(username: String){

}