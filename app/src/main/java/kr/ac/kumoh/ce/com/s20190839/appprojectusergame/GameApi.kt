package kr.ac.kumoh.ce.com.s20190839.appprojectusergame

import retrofit2.http.GET

interface GameApi {
    @GET("games")
    suspend fun getGames(): List<Game>
}