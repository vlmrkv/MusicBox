package com.mrkv.musicbox.trackdetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mrkv.musicbox.R
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage

class TrackDetail : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tracks()
        }
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun Tracks() {
    //val sliderList = listOf<Uri>()
    // Display 10 items
    val pagerState = rememberPagerState(pageCount = {
        10
    })
    Column(
        modifier = Modifier
            .fillMaxHeight()
    ) {

        HorizontalPager(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            state = pagerState
        ) { page ->
            // Our page content
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Top
            ) {
                CoilImage(
                    imageModel = { "https://images.unsplash.com/photo-1701676639172-421b5e0b148b" },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                        //lignment.Center
                    ),
                    modifier = Modifier
                        .padding(10.dp)
                        .size(370.dp)
                        .clip(RoundedCornerShape(15.dp))
                )

                Text(
                    text = "Track title",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    fontFamily = fontFamily,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                LinearProgressIndicator(
                    modifier = Modifier
                        .padding(20.dp)
                        .align(Alignment.CenterHorizontally),
                    color = Color.Cyan,
                    progress = 0.3f
                )
                Row(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Image(
                        painter = painterResource(R.drawable.prev_button),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp),
                    )

                    Image(
                        painter = painterResource(R.drawable.play_button),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp),
                    )

                    Image(
                        painter = painterResource(R.drawable.next_button),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp),
                    )
                }
            }
        }
    }
}

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val fontName = GoogleFont("Roboto")

val fontFamily = FontFamily(
    Font(googleFont = fontName, fontProvider = provider)
)