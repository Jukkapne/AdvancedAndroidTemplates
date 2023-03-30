package com.example.lessontemplate.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lessontemplate.data.Game
import com.example.lessontemplate.data.GameAPI
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.http.HTTP

class GameViewModel: ViewModel() {

    val games = mutableStateListOf<Game>()


    init {
        viewModelScope.launch {
            GameAPI.instance?.apply {
                games.clear()
                getGames().body()?.let {
                    games.addAll(it)
                }
            }
        }
    }

}