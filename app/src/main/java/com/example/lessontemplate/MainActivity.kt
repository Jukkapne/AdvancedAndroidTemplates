package com.example.lessontemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.lessontemplate.ui.theme.LessonTemplateTheme
import com.example.lessontemplate.view.*
import com.example.lessontemplate.viewmodel.FirebaseViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = FirebaseViewModel()
        setContent {
            LessonTemplateTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    //CameraPermissionsView()
                    //MainAuthView(viewModel)
                    //AuthExample()
                    //GameView()
                    //ShowStorageImage()
                    //PickImage()
                    //AnimAppear()
                    //AnimValues()
                    //AnimInf()


                }
            }
        }
    }
}
