package com.example.dig.data.repository

import androidx.lifecycle.LiveData
import com.example.dig.data.db.entity.Opinion
import com.example.dig.data.db.entity.Vote
import com.example.dig.data.db.util.VoteDetail
import com.example.dig.data.db.util.VoteResume
import com.example.dig.data.network.post.Transaction
import com.example.dig.data.network.response.StatusResponse

interface DigRepository {

    suspend fun getVotesResume(): LiveData<List<VoteResume>>

    suspend fun getVoteDetailByPosition(str: Array<String>): LiveData<VoteDetail>

    suspend fun getVoteDetailById(id_vote : Int): LiveData<VoteDetail>

    suspend fun postOpinion(opinion: Opinion)

    suspend fun postVote(vote: Vote): StatusResponse

}