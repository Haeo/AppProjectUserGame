package kr.ac.kumoh.ce.com.s20190839.appprojectusergame

data class Game(
    val id: Int,
    val title: String,
    val release_date: String,
    val developer: String,
    val genre: String,
    val description: String?
)
