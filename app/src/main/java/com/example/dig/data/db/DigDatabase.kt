package com.example.dig.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dig.data.db.entity.Opinion
import com.example.dig.data.db.entity.Vote

@Database(
    entities = [Vote::class],
    version = 2
)
abstract class DigDatabase : RoomDatabase(){

    abstract fun currentVotesDao(): CacheVotesDao

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