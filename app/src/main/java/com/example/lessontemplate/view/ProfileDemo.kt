package com.example.lessontemplate.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.lessontemplate.viewmodel.FirebaseViewModel

/**
 * Main view selects the composable based on the logged in user
 */
@Composable
fun MainView() {
    val vm : FirebaseViewModel = viewModel()

    if(vm.user.value == null){
        RegisterView()
    }else{
        ProfileView()
    }
}

/**
 * Register view contains both register and login options
 */
@Composable
fun RegisterView() {
    val vm : FirebaseViewModel = viewModel()

    var login by remember {  mutableStateOf<Boolean>(false) }
    var username by remember { mutableStateOf<String>("") }
    var pw by remember { mutableStateOf<String>("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    var text = if(!login) "Login with existing account" else "Register new account"

    //Launcher for setting the local image uri when selecting image in the gallery.
    var launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()){
        imageUri = it
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CustomTextField("Username (email)", username ){ username = it }
        CustomTextField("Password", pw, true ){ pw = it }

        //Registering components for selecting profile picture
        //Button launches the gallery selection
        if(!login) {
            Button(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Select profile picture")
            }

            Image(
                painter = rememberAsyncImagePainter(model = imageUri),
                contentDescription = "image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Black, CircleShape),
                contentScale = ContentScale.FillBounds
            )
        }

        Button(
            onClick =
            {
                if(login)
                    vm.signInUser(username, pw)
                else
                    vm.registerUser(username, pw, imageUri)

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if(login) "Log in" else "Register user")
        }

        //Clickable text (preventing click effect). Toggles between register/login view
        val interactionSource = MutableInteractionSource()
        Text(
            text = text,
            Modifier.clickable(interactionSource = interactionSource, indication = null) { login = !login },
            fontSize = 18.sp,
        )
    }
}

/**
 * Profile view contains user information
 */
@Composable
fun ProfileView() {
    var vm: FirebaseViewModel = viewModel()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = "Welcome ${vm.user.value?.email.toString()}!!",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        //Showing progress indicator on top of the image frame if still
        //updloading/downloading. After registration the indicator waits that the image is
        //uploaded to Firebase and public url fetched. Recomposes when the view model image url is ready.
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = rememberAsyncImagePainter(model = vm.profileImageUrl.value) ,
                contentDescription = "image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Black, CircleShape),
                contentScale = ContentScale.FillBounds
            )
            if(vm.profileImageUrl.value == null)
                CircularProgressIndicator()
        }


        Button(onClick = { vm.logout() }) {
            Text(text = "Log out")
        }
    }
    
}

/**
 * Helper composable for creating text field
 */
@Composable
fun CustomTextField(label: String, text: String, isPw: Boolean = false, onchange: (String) -> Unit) {

    var visualTrans: VisualTransformation = if(isPw) PasswordVisualTransformation() else VisualTransformation.None

    OutlinedTextField(
        value = text,
        onValueChange = { onchange(it) },
        label = { Text(text = label) },
        visualTransformation = visualTrans,
        modifier = Modifier.fillMaxWidth()
    )

}