package com.example.dig.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dig.data.db.converter.DateConverter
import com.example.dig.data.db.entity.Balance
import com.example.dig.data.db.entity.Opinion
import com.example.dig.data.db.entity.Vote

@Database(
    entities = [Vote::class, Balance::class],
    version = 4
)
@TypeConverters(DateConverter::class)
abstract class DigDatabase : RoomDatabase(){

    abstract fun currentVotesDao(): CacheVotesDao
    abstract fun currentBalanceDao(): CacheDignitasDao

    companion object {
        @Volatile private var instance: DigDatabase? = null
        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: buildDatabase(context).also {  instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, DigDatabase::class.java, "dig.db").build()

    }
}