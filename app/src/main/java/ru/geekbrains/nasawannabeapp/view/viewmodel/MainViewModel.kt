package ru.geekbrains.nasawannabeapp.view.viewmodel

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.geekbrains.nasawannabeapp.BuildConfig
import ru.geekbrains.nasawannabeapp.repository.PODServerResponseData
import ru.geekbrains.nasawannabeapp.repository.RetrofitImpl
import ru.geekbrains.nasawannabeapp.view.AppState
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainViewModel (
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val retrofitImpl: RetrofitImpl = RetrofitImpl(),
) : ViewModel() {

    companion object {
        private const val API_ERROR = "You need API Key"
        private const val UNKNOWN_ERROR = "Unidentified error"
    }

    private val PODCallback = object : Callback<PODServerResponseData> {

        override fun onResponse(
            call: Call<PODServerResponseData>,
            response: Response<PODServerResponseData>,
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveDataToObserve.postValue(AppState.Success(response.body()!!))
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveDataToObserve.postValue(AppState.Error(Throwable(UNKNOWN_ERROR)))
                } else {
                    liveDataToObserve.postValue(AppState.Error(Throwable(message)))
                }
            }
        }

        override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
            liveDataToObserve.postValue(AppState.Error(t))
        }
    }

    fun getLiveData(): LiveData<AppState> {
        return liveDataToObserve
    }

    fun getPODFromServer(day: Int) {
        val date = getDate(day)
        liveDataToObserve.postValue(AppState.Loading)
        val apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            AppState.Error(Throwable(API_ERROR))
        } else {
            retrofitImpl.getPictureOfTheDay(apiKey, date, PODCallback)
        }
    }

    private fun getDate(day: Int): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val yesterday = LocalDateTime.now().minusDays(day.toLong())
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return yesterday.format(formatter)
        } else {
            val cal: Calendar = Calendar.getInstance()
            val s = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            cal.add(Calendar.DAY_OF_YEAR, (-day))
            return s.format(cal.time)
        }
    }
}