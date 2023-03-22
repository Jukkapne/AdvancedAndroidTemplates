package com.example.lessontemplate

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

//In this exercise you may use the FirebaseAuthViewModel and expand it if needed

@Composable
fun MainAuthView() {
    //Check here if the user is logged in and show either login or welcome screen.
    //Use the viewmodel so that this composable is recomposed if the user status changes
}

@Composable
fun LoginForm() {

    //Add functionality to login the user

    var username by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)) {

        OutlinedTextField(
            value = username, 
            onValueChange = {username=it},
            label =   { Text(text = "Username")},
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = pw,
            onValueChange = {pw=it},
            label = { Text(text = "Password")},
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Sign in")
        }
    }
}

@Composable
fun Welcome() {
    //Add here view that shows welcome message with the user's email.
    //Add also button for log out

    //You may also add here functionality to fetch the personal message for the user
    //from firebase (the message is authorized only by the user). Use mutable state in viewmodel
    //for the message.

    //One more functionality you may add is to create textfield for user to be able
    //to update the personal message.
}