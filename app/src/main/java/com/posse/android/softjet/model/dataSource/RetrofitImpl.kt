package com.posse.android.softjet.model.dataSource

import com.posse.android.softjet.model.DataSource
import com.posse.android.softjet.model.data.Response
import javax.inject.Inject

class RetrofitImpl @Inject constructor(
    private val apiService: ApiService
) : DataSource<Response> {

    override suspend fun getData(isOnline: Boolean, page: Int): Response = apiService.getUsers(page)

    override fun saveData(data: Response) = Unit
}