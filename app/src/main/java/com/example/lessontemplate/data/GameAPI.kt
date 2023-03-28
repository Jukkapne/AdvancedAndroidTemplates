package com.example.lessontemplate.data
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

interface GameAPI {

    @GET("games")
    suspend fun getGames(): List<Game>

    /*@GET("games")
    suspend fun getGames2(): List<Map<String, Any>>*/

    @GET("game")
    suspend fun getGame( @Query("id") id: Int  ): Game

    // https://www.freetogame.com/api/games?platform=browser&category=mmorpg
    

    companion object{
        private const val BASE_URL = "https://www.freetogame.com/api/"

        val instance: GameAPI? by lazy{
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GameAPI:: class.java)
        }
    }
}