package ru.geekbrains.nasawannabeapp.repository

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

interface RetrofitAPI {
    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String): Call<PODServerResponseData>

    @GET("DONKI/FLR")
    fun getSolarFlare(
        @Query("api_key") apiKey: String,
        @Query("startDate") startDate: String = "2021-09-01",
        @Query("endDate") endDate: String = "2021-09-30",
    ): Call<List<PODServerResponseData>>

    @GET("DONKI/FLR")
    fun getSolarFlareToday(
        @Query("api_key") apiKey: String,
        @Query("startDate") startDate: String = "2021-09-07",
    ): Call<List<SolarFlareResponseData>>
}
