package com.example.dig.data.repository

import androidx.lifecycle.LiveData
import com.example.dig.data.db.entity.Opinion
import com.example.dig.data.db.util.VoteDetail
import com.example.dig.data.db.util.VoteResume

interface DigRepository {

    suspend fun getVotesResume(): LiveData<List<VoteResume>>

    suspend fun getVoteDetailById(id_vote : Int): LiveData<VoteDetail>

    suspend fun postOpinion(opinion: Opinion)

}