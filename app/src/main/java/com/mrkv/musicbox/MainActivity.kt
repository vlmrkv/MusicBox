package com.mrkv.musicbox

import android.os.Bundle
import android.util.Log
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
import androidx.compose.foundation.lazy.itemsIndexed
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
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://deezerdevs-deezer.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData("eminem")
        Log.d("debug13", "on create")
        retrofitData.enqueue(object : Callback<MyData> {
            override fun onResponse(
                call: Call<MyData?>, response: Response<MyData?>
            ) {
                // if the api call is a success then this method is executed
                Log.d("debug13", "on response")
                val dataList = response.body()?.data
                if (dataList != null) {
                    for (i in dataList) {
                        Log.d("debug13", "dataList = $i")
                    }
                }
                setContent {
                    NavGraph(dataList)
                }

            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                // if the api call is a failure then this method is executed
                Log.d("debug13", "on failure ${t.message}")
            }
        })
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavGraph(dataList: List<Data>?) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable("main") {
            if (dataList != null) {
                TrackList(navController, dataList)
            }
        }
        composable("track_detail") { Tracks() }
    }
}

@Composable
fun TrackList(navController: NavController, dataList: List<Data>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        itemsIndexed(dataList) { _, data ->
            TrackListItem(
                navController, cover = data.album.cover,
                title = data.title,
                name = data.artist.name
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TrackListItem(
    navController: NavController, cover: String, title: String, name: String
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
                    imageModel = { cover },
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
                        text = title,
                        fontSize = 24.sp
                    )
                    Text(
                        text = name,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

