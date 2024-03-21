package com.example.lessontemplate.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.lessontemplate.viewmodel.FirebaseViewModel
import coil.compose.rememberImagePainter

//In this exercise you may use the FirebaseAuthViewModel and expand it if needed

@Composable
fun MainAuthView(viewModel: FirebaseViewModel ) {
    //Check here if the user is logged in and show either login or welcome screen.
    //Use the viewmodel so that this composable is recomposed if the user status changes

    //val vm : FirebaseViewModel = viewModel()
    val vm = viewModel

    if(vm.user.value == null){
        LoginForm(vm)
    }else{
        Welcome(vm)
    }

}

@Composable
fun LoginForm(viewModel: FirebaseViewModel) {
    val imageUri by remember { viewModel.profileImageUrl }
    val context = LocalContext.current
    //val vm : FirebaseViewModel = viewModel()
    val vm = viewModel
    //Add functionality to login the user
    var username by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)) {

        OutlinedTextField(
            value = username, 
            onValueChange = {username=it},
            label =   { Text(text = "Username (email)")},
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
            onClick = { vm.signInUser(username, pw) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Sign in")
        }
    }
}

@Composable
fun Welcome(viewModel: FirebaseViewModel) {
    val vm = viewModel
    vm.getPersonalMessage()
    //Add here view that shows welcome message with the user's email.
    //Add also button for log out

    //You may also add here functionality to fetch the personal message for the user
    //from firebase (the message is authorized only by the user). Use mutable state in viewmodel
    //for the message.

    //One more functionality you may add is to create textfield for user to be able
    //to update the personal message.
    Column {
        Text(
            text = "Welcome " + (vm.user.value?.email ?: "Guest"),
            fontSize = 24.sp
        )
        Button(onClick = { vm.logout() }) {
            Text(text = "Log out")
        }
        Text(text = vm.msg.value ?: "")

        // Kuvan näyttäminen
        vm.profileImageUrl.value?.let { imageUrl ->
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = "Profile Image",
                modifier = Modifier.fillMaxWidth() // Tai muu haluamasi muokkain
            )
        }
    }
}