package com.example.dig.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.dig.data.db.converter.DateConverter
import java.util.*

@Entity(tableName = "cache_dignitas_balance")
data class Balance (
    @PrimaryKey
    val timestamp: Date,
    val value: Int
)