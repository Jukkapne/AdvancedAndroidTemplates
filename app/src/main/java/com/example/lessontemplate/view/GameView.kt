package com.example.lessontemplate.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.rememberAsyncImagePainter
import com.example.lessontemplate.data.Game
import com.example.lessontemplate.data.Route
import com.example.lessontemplate.viewmodel.GameViewModel
import okhttp3.internal.connection.RouteException


@Composable
fun GameView() {
    val vm: GameViewModel = viewModel()
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.Games.name){
        composable(Route.Games.name){
            //Passing the function as last parameter to provide the GameList
            //means of accessing viewmodel getGameById
            GameList(games = vm.games ){gameId ->
                navController.navigate( "${Route.GameInfo.name}/${gameId}" )
            }
        }
        composable(
            "${Route.GameInfo.name}/{gameId}",
            arguments = listOf(navArgument("gameId"){type= NavType.IntType})
        ){navEntry ->
            vm.getGameById(navEntry.arguments?.getInt("gameId"))
            vm.game.value?.let {
                GameInfo(it) {
                    navController.navigate(Route.Games.name)
                }
            }
        }
    }
}


@Composable
fun GameList(games: List<Game>, selectGame: (id: Int) -> Unit) {

    LazyColumn(){
        items(games){game->
            Card(elevation = 10.dp, modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .clickable { selectGame(game.id) },
            ) {
                Text(text = game.title, modifier = Modifier.padding(10.dp),)
            }
        }
    }
}


@Composable
fun GameInfo(game: Game, showGames: () -> Unit) {

    val imageUrl = if(game.screenshots.isNotEmpty()) game.screenshots[0].image else game.thumbnail

    Card(
        elevation = 10.dp, modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)
        ) {

            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = "game image",
                modifier = Modifier
                    .clickable { showGames() }
                    .padding(5.dp)
                    .border(width = 2.dp, color = Color.Black)
            )

            Text(text = game.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}