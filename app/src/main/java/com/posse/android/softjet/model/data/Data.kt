package com.posse.android.softjet.model.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.posse.android.softjet.model.data.Data.Companion.TABLE_NAME
import kotlinx.parcelize.Parcelize

@Entity(tableName = TABLE_NAME)
@Parcelize
data class Data(
    val avatar: String,
    val email: String,
    val first_name: String,
    @PrimaryKey val id: Int,
    val last_name: String
) : Parcelable {
    companion object {
        const val TABLE_NAME = "Basket"
    }
}