package com.example.lessontemplate.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lessontemplate.data.Game
import com.example.lessontemplate.data.GameAPI
import kotlinx.coroutines.launch

class GameViewModel: ViewModel() {
    fun getGameList(){
        viewModelScope.launch {
            GameAPI.instance?.apply {
                val games: List<Game> = getGames()

                games.forEach {
                    Log.e("--", it.title)
                }

            }
        }
    }

    fun getGame(id: Int){
        viewModelScope.launch {
            GameAPI.instance?.apply {
                val game: Game = getGame(id)
                Log.e("--", game.description)
            }
        }
    }

}