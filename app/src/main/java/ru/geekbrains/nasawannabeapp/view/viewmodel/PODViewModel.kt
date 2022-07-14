package ru.geekbrains.nasawannabeapp.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.geekbrains.nasawannabeapp.BuildConfig
import ru.geekbrains.nasawannabeapp.repository.PODServerResponseData
import ru.geekbrains.nasawannabeapp.repository.RetrofitImpl

class PODViewModel (
    private val PODDataToObserve: MutableLiveData<PODdata> = MutableLiveData(),
    private val retrofitImpl: RetrofitImpl = RetrofitImpl()
) : ViewModel() {

    fun getPODLiveData(): LiveData<PODdata> {
        return PODDataToObserve
    }

    fun sendServerRequest() {
        PODDataToObserve.value = PODdata.Loading
        val apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            PODdata.Error(Throwable("API key parameter is empty"))
        } else {
            retrofitImpl.getPictureOfTheDay(apiKey, podCallback)
        }
    }

    private val podCallback = object : Callback<PODServerResponseData> {
        override fun onResponse(
            call: Call<PODServerResponseData>,
            response: Response<PODServerResponseData>
        ) {
            if (response.isSuccessful && response.body() != null) {
                PODDataToObserve.value = PODdata.Success(response.body()!!)
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    PODDataToObserve.value = PODdata.Error(Throwable("Unknown error"))
                } else {
                    PODDataToObserve.value = PODdata.Error(Throwable(message))
                }
            }
        }
        override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
            PODDataToObserve.value = PODdata.Error(t)
        }
    }
}
