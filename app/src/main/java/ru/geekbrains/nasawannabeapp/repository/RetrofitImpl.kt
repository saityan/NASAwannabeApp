package ru.geekbrains.nasawannabeapp.repository

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class RetrofitImpl {

    private val baseUrl = "https://api.nasa.gov/"

    private val api by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory
                .create(GsonBuilder()
                    .setLenient()
                    .create()))
            .client(createOkHttpClient(PODInterceptor()))
            .build()
            .create(RetrofitAPI::class.java)
    }

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }

    fun getPictureOfTheDay(apiKey: String, podCallback: Callback<PODServerResponseData>) {
        api.getPictureOfTheDay(apiKey).enqueue(podCallback)
    }

    fun getSolarFlareToday(
        apiKey: String,
        podCallback: Callback<List<SolarFlareResponseData>>,
        startDate: String = "2021-09-07"
    ) {
        api.getSolarFlareToday(apiKey, startDate).enqueue(podCallback)
    }

    inner class PODInterceptor : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            return chain.proceed(chain.request())
        }
    }
}
