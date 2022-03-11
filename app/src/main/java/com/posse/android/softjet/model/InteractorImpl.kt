package com.posse.android.softjet.model

import com.posse.android.softjet.di.annotations.Local
import com.posse.android.softjet.model.data.Data
import com.posse.android.softjet.model.data.Response
import javax.inject.Inject

class InteractorImpl @Inject constructor(
    private val remoteDataSource: DataSource<Response>,
    @Local private val localDataSource: DataSource<Response>
) : DataSource<Response> {

    override suspend fun getData(isOnline: Boolean, page: Int): Response {
        return if (isOnline) {
            val data = remoteDataSource.getData(isOnline, page)
            saveData(data)
            data
        } else localDataSource.getData(isOnline, page)
    }

    override fun saveData(data: Response) = localDataSource.saveData(data)
}