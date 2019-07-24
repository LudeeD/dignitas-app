package com.example.dig.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cache_votes_table")
data class Vote(
    @PrimaryKey
    val id: String,
    val title: String,
    val info: String,
    @Embedded(prefix = "pos_")
    val location: Location,
    @Embedded(prefix = "status_")
    val status: Status,
    val timestamp: Int
)
