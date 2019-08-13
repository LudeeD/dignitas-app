package com.example.dig.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.dig.data.db.entity.Balance

@Dao
interface CacheDignitasDao {

    @Insert(onConflict = REPLACE)
    fun addValue(balance: Balance)

    @Query("SELECT * FROM cache_dignitas_balance ORDER BY timestamp DESC LIMIT 1")
    fun getCachedBalance(): LiveData<Balance>

    @Query("SELECT * FROM cache_dignitas_balance ORDER BY timestamp DESC LIMIT :n")
    fun getLastNBalance(n: Int) : LiveData<List<Balance>>
}