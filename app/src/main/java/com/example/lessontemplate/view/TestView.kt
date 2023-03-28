package com.example.lessontemplate.view

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lessontemplate.viewmodel.FirebaseViewModel
import com.example.lessontemplate.viewmodel.GameViewModel

@Composable
fun TestView() {
    val vm: GameViewModel = viewModel()

    Button(onClick = { vm.getGame(4)  }) {
        Text(text = "OK")
    }
}