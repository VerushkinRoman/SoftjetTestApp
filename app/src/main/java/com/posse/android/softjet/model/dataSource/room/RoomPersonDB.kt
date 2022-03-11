package com.posse.android.softjet.model.dataSource.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.posse.android.softjet.model.data.Data

@Database(
    entities = [Data::class],
    version = 1
)
abstract class RoomPersonDB : RoomDatabase() {

    abstract val personDao: PersonDao
}