package com.mrkv.musicbox

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {
    @Headers("X-RapidAPI-Key: d221fc9110msh4a3650a5b7df4b1p1fee05jsnfc1195f57dd2",
        "X-RapidAPI-Host: deezerdevs-deezer.p.rapidapi.com")

    @GET("search")
    fun getData(@Query("q") query: String) : Call<List<MyMusicData>>
}