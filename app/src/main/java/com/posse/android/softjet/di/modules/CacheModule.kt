package com.posse.android.softjet.di.modules

import android.content.Context
import androidx.room.Room
import com.posse.android.softjet.model.dataSource.room.RoomPersonDB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule {

    @Provides
    @Singleton
    fun db(context: Context): RoomPersonDB {
        return Room.databaseBuilder(context, RoomPersonDB::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}