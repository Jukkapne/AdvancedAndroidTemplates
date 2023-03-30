package com.example.lessontemplate.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.lessontemplate.data.Game
import com.example.lessontemplate.viewmodel.GameViewModel

@Composable
fun GameList() {
    val vm: GameViewModel = viewModel()
    var selectedGame by remember { mutableStateOf<Game?>(null) }

    selectedGame?.let {
        Column() {
            GameInfo(game = it)
        }

        return;
    }
    
    LazyColumn(){
        items(vm.games){game->
            Card(elevation = 10.dp, modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .clickable { selectedGame = game },
            ) {
                Text(text = game.title, modifier = Modifier.padding(10.dp),)
            }
        }
    }
}


@Composable
fun Test(getGames: ()->Unit) {
    getGames()
}

@Composable
fun GameInfo(game: Game) {
    Card(
        elevation = 10.dp, modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Image(
                painter = rememberAsyncImagePainter(model = game.thumbnail),
                contentDescription = "game thumbnail"
            )

            Text(text = game.title)
        }
    }
}