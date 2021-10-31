package ru.geekbrains.nasawannabeapp.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.geekbrains.nasawannabeapp.BuildConfig
import ru.geekbrains.nasawannabeapp.repository.PODRetrofitImpl
import ru.geekbrains.nasawannabeapp.repository.PODServerResponseData
import ru.geekbrains.nasawannabeapp.repository.SolarFlareResponseData

class PODViewModel (
    private val liveDataToObserve: MutableLiveData<PODdata> = MutableLiveData(),
    private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl(),
) : ViewModel() {

    fun getLiveData(): LiveData<PODdata> {
        return liveDataToObserve
    }

    fun sendServerRequest() {
        liveDataToObserve.value = PODdata.Loading
        val apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            PODdata.Error(Throwable("API key parameter is empty"))
        } else {
           retrofitImpl.getPictureOfTheDay(apiKey, podCallback)
            retrofitImpl.getSolarFlareToday(apiKey, solarFlareCallback, "2021-09-01")
        }
    }

        val podCallback = object : Callback<PODServerResponseData> {
        override fun onResponse(
            call: Call<PODServerResponseData>,
            response: Response<PODServerResponseData>
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveDataToObserve.value = PODdata.Success(response.body()!!)
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveDataToObserve.value = PODdata.Error(Throwable("Unknown error"))
                } else {
                    liveDataToObserve.value = PODdata.Error(Throwable(message))
                }
            }
        }
        override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
            liveDataToObserve.value = PODdata.Error(t)
        }
    }

    val solarFlareCallback = object : Callback<List<SolarFlareResponseData>> {
        override fun onResponse(
            call: Call<List<SolarFlareResponseData>>,
            response: Response<List<SolarFlareResponseData>>
        ) {
            if (response.isSuccessful && response.body() != null) {} else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveDataToObserve.value = PODdata.Error(Throwable("Unknown error"))
                } else {
                    liveDataToObserve.value = PODdata.Error(Throwable(message))
                }
            }
        }
        override fun onFailure(call: Call<List<SolarFlareResponseData>>, t: Throwable) {
            liveDataToObserve.value = PODdata.Error(t)
        }
    }
}