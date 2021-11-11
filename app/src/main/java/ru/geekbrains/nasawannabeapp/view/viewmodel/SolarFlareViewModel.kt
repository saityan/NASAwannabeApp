package ru.geekbrains.nasawannabeapp.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.geekbrains.nasawannabeapp.BuildConfig
import ru.geekbrains.nasawannabeapp.repository.RetrofitImpl
import ru.geekbrains.nasawannabeapp.repository.SolarFlareResponseData

class SolarFlareViewModel (
    private val SolarFlareDataToObserve: MutableLiveData<SolarFlareData> = MutableLiveData(),
    private val retrofitImpl: RetrofitImpl = RetrofitImpl(),
) : ViewModel() {

    fun getSolarFlareLiveData(): LiveData<SolarFlareData> {
        return SolarFlareDataToObserve
    }

    fun sendServerRequest() {
        SolarFlareDataToObserve.value = SolarFlareData.Loading
        val apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            PODdata.Error(Throwable("API key parameter is empty"))
        } else {
            retrofitImpl.getSolarFlareToday(apiKey, solarFlareCallback, "2021-10-31")
        }
    }

    private val solarFlareCallback = object : Callback<List<SolarFlareResponseData>> {
        override fun onResponse(
            call: Call<List<SolarFlareResponseData>>,
            response: Response<List<SolarFlareResponseData>>
        ) {
            if (response.isSuccessful && response.body() != null) {
                SolarFlareDataToObserve.value = SolarFlareData.Success(response.body()!![0])
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    SolarFlareDataToObserve.value = SolarFlareData.Error(Throwable("Unknown error"))
                } else {
                    SolarFlareDataToObserve.value = SolarFlareData.Error(Throwable(message))
                }
            }
        }
        override fun onFailure(call: Call<List<SolarFlareResponseData>>, t: Throwable) {
            SolarFlareDataToObserve.value = SolarFlareData.Error(t)
        }
    }
}