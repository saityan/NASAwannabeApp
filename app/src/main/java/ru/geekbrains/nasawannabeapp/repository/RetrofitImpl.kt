package ru.geekbrains.nasawannabeapp.repository

import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitImpl {

    companion object {
        private const val BASE_URL = "https://api.nasa.gov/"
    }

    private val api by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
            .create(RetrofitAPI::class.java)
    }

    fun getPictureOfTheDay(apiKey: String, date: String, podCallback: Callback<PODServerResponseData>) {
        api.getPictureOfTheDay(apiKey, date).enqueue(podCallback)
    }
}