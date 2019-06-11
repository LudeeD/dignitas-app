package com.example.dig.data.db.util

import androidx.room.ColumnInfo

class VoteResume (
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "pos_lat")
    val lat: Double,
    @ColumnInfo(name = "pos_lng")
    val lng: Double,
    @ColumnInfo(name = "status_typeX")
    val status: String
)