package com.example.lessontemplate

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lessontemplate.viewmodel.BlogViewModel

@Composable
fun FirstExercise() {
    var username by remember { mutableStateOf("") }
    var msg by remember { mutableStateOf("") }
    val vm: BlogViewModel = viewModel()

    Column() {
        OutlinedTextField(value = username, onValueChange = { x -> username = x } )
        OutlinedTextField(value = msg, onValueChange = { x -> msg = x } )
        Button(onClick = { vm.addBlog(username, msg) }) {
            Text("Add blog")
        }
    }
}

@Composable
fun MessageUI() {

    val vm: BlogViewModel = viewModel()

    Column() {
        Button(onClick = { vm.getBlogs() }) {
            Text(text = "Get messages")
        }
        LazyColumn(){
            items(items= vm.blogs){
                Divider(thickness = 5.dp)
                Text(text = it)
            }
        }
    }
}



