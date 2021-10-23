package ru.geekbrains.nasawannabeapp.view.viewmodel

import ru.geekbrains.nasawannabeapp.repository.PODServerResponseData

sealed class PODdata {
    data class Success(val serverResponseData: PODServerResponseData) : PODdata()
    data class Error(val error: Throwable) : PODdata()
    object Loading : PODdata()
}