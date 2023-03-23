package com.example.lessontemplate.view

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


@Composable
fun ShowStorageImage() {
    var byteArray by remember { mutableStateOf<ByteArray?>(null) }

    var painter = rememberAsyncImagePainter(
        model = byteArray
    )

    Column() {
        Button(onClick = {
            Firebase.storage.reference
                .child("moth.jpg")
                .getBytes(1024*1024)
                .addOnSuccessListener {
                    byteArray = it
                }
        }) {
            Text(text = "Get image")
        }
        Image(painter = painter , contentDescription = "images")
    }
}

@Composable
fun PickImage() {
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    var launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()){
        imageUri = it
    }
    
    Column() {
        Button(onClick = { launcher.launch("image/*") }) {
            Text(text = "Open image")
        }
        Button(onClick = { fileToStorage(imageUri) }) {
            Text(text = "Send image to storage")
        }
    }
}

fun fileToStorage(uri: Uri?){
    var imageRef = Firebase.storage.reference.child("profile.jpg")

    uri?.let{ u ->
        imageRef.putFile(u)
            .addOnSuccessListener {
                Log.d("***", "Image uploaded")
            }
            .addOnFailureListener{
                Log.e("***", it.message.toString())
            }
    }
}