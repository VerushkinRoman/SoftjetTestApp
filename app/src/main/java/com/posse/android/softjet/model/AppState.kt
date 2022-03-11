package com.posse.android.softjet.model

sealed interface AppState<T> {
    data class Success<T>(val data: T) : AppState<T>
    data class Error(val error: Throwable?) : AppState<Nothing>
    object Loading : AppState<Nothing>
}