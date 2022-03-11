package com.posse.android.softjet.model.dataSource.room

import com.posse.android.softjet.model.DataSource
import com.posse.android.softjet.model.data.Response
import javax.inject.Inject

class RoomImpl @Inject constructor(
    private val dataBase: RoomPersonDB
) : DataSource<Response> {

    override suspend fun getData(isOnline: Boolean, page: Int): Response {
        val persons =
            dataBase.personDao.loadPersonsByPage((page - 1) * PAGE_SIZE, PAGE_SIZE) ?: listOf()
        val maxElement = dataBase.personDao.getCount()
        return Response(persons, page, PAGE_SIZE, maxElement, maxElement / PAGE_SIZE)
    }

    override fun saveData(data: Response) {
        dataBase.personDao.insert(data.data)
    }

    companion object {
        private const val PAGE_SIZE = 6
    }
}