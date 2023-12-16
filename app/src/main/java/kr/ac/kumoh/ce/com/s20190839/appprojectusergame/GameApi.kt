package kr.ac.kumoh.ce.com.s20190839.appprojectusergame

import retrofit2.http.GET

// 인터페이스
interface GameApi {
    @GET("games")
    suspend fun getGames(): List<Game>
}