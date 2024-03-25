package com.example.lessontemplate.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
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
import com.example.lessontemplate.data.MinimumSystemRequirements
import com.example.lessontemplate.data.Route
import com.example.lessontemplate.viewmodel.GameViewModel
import okhttp3.internal.connection.RouteException


@Composable
fun GameView() {
    val vm: GameViewModel = viewModel()
    val navController = rememberNavController()

    //Function for selecting game by id and navigating to game info view
    val selectGame: (Int) -> Unit = {gameId ->
        vm.getGameById(gameId)
        vm.getReviews(gameId.toString())
        navController.navigate(Route.GameInfo.name)
    }


    //Function for adding review text for game with id
    val addReview: (String, String) -> Unit = { id, text ->
        vm.addReview(id, text)
    }

    //Function for navigating to the game list
    val showGame: () -> Unit = { navController.navigate(Route.Games.name) }

    NavHost(navController = navController, startDestination = Route.Games.name){
        composable(Route.Games.name){
            GameList(vm.games, selectGame)
        }
        composable(Route.GameInfo.name){
            vm.game.value?.let { game ->
                GameInfo(
                    game,
                    vm.reviews,
                    addReview,
                    showGame
                )
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
fun GameInfo(
    game: Game,
    reviews: List<String>,
    addReview: (String, String) -> Unit,
    showGames: () -> Unit
) {

    var reviewText by remember { mutableStateOf<String>("") }
    val imageUrl = if(game.screenshots.isNotEmpty()) game.screenshots[0].image else game.thumbnail

    Column(modifier = Modifier.padding(10.dp)) {
        Card( elevation = 10.dp, modifier = Modifier.fillMaxWidth()) {
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
        Text(text = "Add review")
        OutlinedTextField(
            value = reviewText,
            onValueChange = {reviewText = it},
            modifier = Modifier.height(200.dp)
        )
        Button(onClick = { addReview(game.id.toString(), reviewText) }) {
            Text(text = "Submit review")
        }
        Reviews(reviews)
    }
}

@Composable
fun Reviews(reviews: List<String>) {
    LazyColumn(){
        items(reviews){
            Text(text = it.toString(), modifier = Modifier
                .padding(5.dp)
                .border(width = 1.dp, color = Color.Black)
                .padding(5.dp)
            )
        }
    }
}

@Composable
fun SubmitGameForm(vm: GameViewModel) {
    // State variables for Game details
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    // Add other necessary fields according to your Game data class

    // Minimum System Requirements details
    var graphics by remember { mutableStateOf("") }
    var memory by remember { mutableStateOf("") }
    var os by remember { mutableStateOf("") }
    var processor by remember { mutableStateOf("") }
    var storage by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        // Fields for Game information input
        // ...

        // Fields for Minimum System Requirements input
        // ...

        Button(onClick = {
            val game = Game(
                title = title,
                description = description,
                developer = "Unknown", // Placeholder value
                freetogame_profile_url = "",
                game_url = "",
                genre = "Not Specified", // Placeholder or a default value
                id = 0, // Assuming this might be generated by the backend upon creation
                minimum_system_requirements = MinimumSystemRequirements(
                    graphics = graphics,
                    memory = memory,
                    os = os,
                    processor = processor,
                    storage = storage
                ),
                platform = "PC", // Example default value
                publisher = "Self-published", // Placeholder value
                release_date = "2024-01-01", // Example placeholder value
                screenshots = emptyList(), // Default to an empty list if not applicable
                short_description = "",
                status = "Active", // Placeholder or a default value
                thumbnail = "" // Default or placeholder image URL
            )
            vm.postGame(game)

        }) {
            Text("Submit Game")
        }
    }
}




