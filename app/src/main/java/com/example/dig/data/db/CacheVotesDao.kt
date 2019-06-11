package com.example.dig.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.dig.data.db.entity.Vote
import com.example.dig.data.db.util.VoteDetail
import com.example.dig.data.db.util.VoteResume

@Dao
interface CacheVotesDao{

    @Insert(onConflict = REPLACE)
    fun insertAll(votes: List<Vote>)

    @Query("SELECT * FROM cache_votes_table")
    fun getCachedVotesResume():LiveData<List<VoteResume>>

    @Query("SELECT * from cache_votes_table WHERE id = :id")
    fun getCachedVoteDetailById(id: Int): LiveData<VoteDetail>
}