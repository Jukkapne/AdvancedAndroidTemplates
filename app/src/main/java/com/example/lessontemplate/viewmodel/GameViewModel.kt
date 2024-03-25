package com.example.lessontemplate.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lessontemplate.data.Game
import com.example.lessontemplate.data.GameAPI
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.http.HTTP

class GameViewModel: ViewModel() {

    val games = mutableStateListOf<Game>()
    val game = mutableStateOf<Game?>(null)
    val reviews = mutableStateListOf<String>()


    init {
        viewModelScope.launch {
            GameAPI.instance?.getGames()?.body()?.let {
                games.clear()
                games.addAll(it)
            }
        }
    }

    /**
     * Gets game by id
     */
    fun getGameById(id: Int?){
        if(id==null){
            return;
        }

        viewModelScope.launch {
            GameAPI.instance?.getGame(id)?.body()?.let{
                game.value = it;
            }
        }
    }

    fun addReview(gameId: String, reviewText: String){
        viewModelScope.launch {
            Firebase.firestore.collection("reviews")
                .add(mapOf("gameId" to gameId, "reviewText" to reviewText))
                .addOnSuccessListener {
                    reviews.add(reviewText)
                }
        }
    }

    fun getReviews(gameId: String){
        viewModelScope.launch {
            Firebase.firestore.collection("reviews")
                .whereEqualTo("gameId", gameId)
                .get()
                .addOnSuccessListener {qs->
                    reviews.clear()
                    val rev = mutableListOf<String>()
                    qs.documents.forEach { doc ->
                        rev.add( doc.get("reviewText").toString() )
                    }
                    reviews.addAll(rev)
                }
        }
    }
    fun postGame(game: Game) {
        viewModelScope.launch {
            try {
                val response = GameAPI.instance?.sendGame(game) // Use 'game', not 'Game'
                // Handle the response, e.g., updating UI state or LiveData to indicate success or failure
                Log.d("GameViewModel", "Response: $response")
            } catch(e: Exception) {
                // Handle error, e.g., log or show an error message
                Log.e("GameViewModel", "Error: $e")
            }
        }
    }


}