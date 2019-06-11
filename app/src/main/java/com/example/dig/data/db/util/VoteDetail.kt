package com.example.dig.data.db.util

import androidx.room.ColumnInfo

class VoteDetail(
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "info")
    val info: String,
    @ColumnInfo(name = "pos_lat")
    val lat: Double,
    @ColumnInfo(name = "pos_lng")
    val lng: Double,
    @ColumnInfo(name = "pos_direction")
    val dir: Double,
    @ColumnInfo(name = "status_typeX")
    val status: String,
    @ColumnInfo(name = "status_trueX")
    val true_votes: Int,
    @ColumnInfo(name = "status_falseX")
    val false_votes: Int,
    @ColumnInfo(name = "status_verdict")
    val verdict: Int,
    @ColumnInfo(name = "timestamp")
    val timestamp: Int
)