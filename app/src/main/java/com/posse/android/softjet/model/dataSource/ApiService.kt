package com.posse.android.softjet.model.dataSource

import com.posse.android.softjet.model.data.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getUsers(@Query("page") page: Int?): Response
}