package kr.ac.kumoh.ce.com.s20190839.appprojectusergame

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage

enum class GameScreen {
    List,
    Detail
}

@Composable
fun GameApp(gameList: List<Game>) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = GameScreen.List.name,
    ) {
        composable(route = GameScreen.List.name) {
            GameList(gameList) {    // 게임 리스트 출력
                navController.navigate(it)
            }
        }
        composable(route = GameScreen.Detail.name + "/{index}",
            arguments = listOf(navArgument("index") {
                type = NavType.IntType
            })
        ) {
            val index = it.arguments?.getInt("index") ?: -1
            if (index >= 0)
                GameDetail(gameList[index])
        }
    }
}

// 게임 리스트 전체 출력
@Composable
fun GameList(list: List<Game>, onNavigateToDetail: (String) -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(list.size) {
            GameItem(it, list[it], onNavigateToDetail)
        }
    }
}

// 리스트 중 각각의 데이터에 대한 출력
@Composable
fun GameItem(index: Int,
             game: Game,
             onNavigateToDetail: (String) -> Unit) {
    Card(   // 카드 스타일
        modifier = Modifier.clickable {
            onNavigateToDetail(GameScreen.Detail.name + "/$index")
        },
        elevation = CardDefaults.cardElevation(8.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(8.dp)
        ) {
            AsyncImage(
                model = "${game.game_img}",
                contentDescription = "게임 이미지",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(percent = 10)),
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                TextTitle(game.title)
                TextDeveloper(game.developer)
            }
        }
    }
}

// 게임명 출력
@Composable
fun TextTitle(title: String) {
    Text(title, fontSize = 30.sp)
}

// 게임 개발사 출력
@Composable
fun TextDeveloper(developer: String) {
    Text(developer, fontSize = 20.sp)
}

// 게임 상세 정보 출력
// 장르 - 게임명 - 게임 이미지 - [개발사 이미지 + 개발사] - 소개 글 - 유튜브 링크
@Composable
fun GameDetail(game: Game) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            game.genre,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            lineHeight = 30.sp,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            game.title,
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            lineHeight = 45.sp
        )
        Spacer(modifier = Modifier.height(16.dp))

        AsyncImage(
            model = "${game.game_img}",
            contentDescription = "게임 이미지",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(400.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "${game.dev_img}",
                contentDescription = "게임 개발사 이미지",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
            Text(game.developer, fontSize = 30.sp)
        }
        Spacer(modifier = Modifier.height(32.dp))

        game.description?.let {
            Text(
                it,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                lineHeight = 35.sp
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.youtube.com/results?search_query=${game.title}")
            )
            startActivity(context, intent, null)
        }) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                YoutubeIcon()
                Spacer(modifier = Modifier.width(16.dp))
                Text("게임 검색", fontSize = 30.sp)
            }
        }
    }
}

// 출처
// https://github.com/worstkiller/jetpackcompose_canvas_icon_pack
// https://github.com/worstkiller/jetpackcompose_canvas_icon_pack/blob/master/app/src/main/java/com/vikas/jetpackcomposeiconpack/ShapesActivity.kt
@Composable
fun YoutubeIcon() {
    Canvas(
        modifier = Modifier
            .size(70.dp)
    ) {

        val path = Path().apply {
            moveTo(size.width * .43f, size.height * .38f)
            lineTo(size.width * .72f, size.height * .55f)
            lineTo(size.width * .43f, size.height * .73f)
            close()
        }
        drawRoundRect(
            color = Color.Red,
            cornerRadius = CornerRadius(40f, 40f),
            size = Size(size.width, size.height * .70f),
            topLeft = Offset(size.width.times(.0f), size.height.times(.20f))
        )
        drawPath(color = Color.White, path = path)
    }
}