package com.example.lessontemplate.data
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface GameAPI {


    @GET("games")
    suspend fun getGames(): Response<List<Game>>

    /*@GET("games")
    suspend fun getGames2(): List<Map<String, Any>>*/

    @GET("game")
    suspend fun getGame( @Query("id") id: Int  ): Response<Game>

    // https://www.freetogame.com/api/games?platform=browser&category=mmorpg
    @GET("games")
    suspend fun getFilteredGames(@Query("platform") platform: String,
        @Query("category") category: String): List<Game>

    @GET("games2")
    suspend fun getFilteredGames2(@QueryMap params: Map<String,String>): List<Game>

    @Headers("Content-type: application/json")
    @POST("game")
    suspend fun sendGame(@Body body: Game, @Field("name") name: String) : Game

//Authorization: Bearer safdölkjsaölkfjölkdsajsa
    @POST
    suspend fun getAuthorizedInformation(@Header("Authorization") bearerToken: String)


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