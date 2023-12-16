package kr.ac.kumoh.ce.com.s20190839.appprojectusergame

// 게임 테이블 정보
data class Game(
    val id: Int,
    val title: String,  // 게임명
    val release_date: String,   // 출시일
    val developer: String,  // 개발사
    val genre: String,  // 장르
    val description: String?,   // 소개 글
    val game_img: String?,  // 게임 대표 이미지 링크(db에 저장)
    val dev_img: String?    // 게임 개발사 대표 이미지 링크(db에 저장)
)
