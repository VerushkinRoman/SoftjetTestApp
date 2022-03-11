package com.posse.android.softjet.model

interface DataSource<T> {
    suspend fun getData(isOnline: Boolean, page: Int): T
    fun saveData(data:T)
}