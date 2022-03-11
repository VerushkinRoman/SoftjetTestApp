package com.posse.android.softjet.model.dataSource.room

import androidx.room.*
import com.posse.android.softjet.model.data.Data

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(person: Data)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg persons: Data)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(persons: List<Data>)

    @Query("DELETE FROM ${Data.TABLE_NAME} WHERE id = :id")
    fun deleteById(id: Long)

    @Query("DELETE FROM ${Data.TABLE_NAME}")
    fun deleteAll()

    @Delete
    fun delete(person: Data)

    @Delete
    fun delete(vararg persons: Data)

    @Delete
    fun delete(persons: List<Data>)

    @Query("SELECT * FROM ${Data.TABLE_NAME}")
    fun getAll(): List<Data>

    @Query("SELECT * FROM ${Data.TABLE_NAME} WHERE id = :id")
    fun getById(id: Int): List<Data>?

    @Query("SELECT * FROM ${Data.TABLE_NAME} LIMIT :quantity OFFSET :start")
    fun loadPersonsByPage(start: Int?, quantity: Int): List<Data>?

    @Query("SELECT COUNT(id) FROM ${Data.TABLE_NAME}")
    fun getCount(): Int
}