package com.posse.android.softjet.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Response(
    val data: List<Data>,
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int
) : Parcelable