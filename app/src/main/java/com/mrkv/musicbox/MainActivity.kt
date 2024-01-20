package com.mrkv.musicbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mrkv.musicbox.trackdetail.Tracks
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://deezerdevs-deezer.p.rapidapi.com/search?q=eminem")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData("eminem")

        retrofitData.enqueue(object : Callback<MyMusicData?> {
            override fun onResponse(
                call: Call<MyMusicData?>, response: Response<MyMusicData?>
            ) {
                // if the api call is a success then this method is executed
                val image = response.body()?.data?.
                val trackTitle = response.body()?.
                val artistName = response.body()?.
            }

            override fun onFailure(call: Call<MyMusicData?>, t: Throwable) {
                // if the api call is a failure then this method is executed
            }
        })

        setContent {
            NavGraph()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable("main") {
            TrackList(navController)
        }
        composable("track_detail") { Tracks() }
    }
}

@Composable
fun TrackList(navController: NavController) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(count = 100) {
            TrackListItem(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TrackListItem(
    navController: NavController,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(5.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        onClick = { navController.navigate("track_detail") }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray),
            Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                CoilImage(
                    imageModel = {  },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    ),
                    modifier = Modifier
                        .padding(10.dp)
                        .size(70.dp)
                        .clip(CircleShape)
                )
                Column {

                    Text(
                        text = "trackTitle",
                        fontSize = 24.sp
                    )
                    Text(text = "artistName", fontSize = 18.sp)
                }
            }
        }
    }
}

