package ru.geekbrains.nasawannabeapp.view.viewmodel

import ru.geekbrains.nasawannabeapp.repository.SolarFlareResponseData

sealed class SolarFlareData {
    data class Success(val serverResponseData: SolarFlareResponseData) : SolarFlareData()
    data class Error(val error: Throwable) : SolarFlareData()
    object Loading : SolarFlareData()
}
