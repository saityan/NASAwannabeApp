package ru.geekbrains.nasawannabeapp.repository

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

interface RetrofitAPI {
    @GET("planetary/apod")
        fun getPictureOfTheDay(
        @Query("api_key") apiKey: String,
        @Query("date") date: String,
    ): Call<PODServerResponseData>
}