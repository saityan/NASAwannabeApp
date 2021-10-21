package ru.geekbrains.nasawannabeapp.view

import ru.geekbrains.nasawannabeapp.repository.PODServerResponseData

sealed class AppState {
    data class Success(val serverResponseData: PODServerResponseData) : AppState()
    data class Error(val error: Throwable) : AppState()
    data class Loading(val progress: Int?) : AppState()
}