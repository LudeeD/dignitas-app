package com.example.dig.data.network

import androidx.lifecycle.LiveData
import com.example.dig.data.db.entity.Balance
import com.example.dig.data.db.entity.Opinion
import com.example.dig.data.db.entity.Vote
import com.example.dig.data.network.response.BalanceResponse
import com.example.dig.data.network.response.StatusResponse
import com.example.dig.data.network.response.VotesResponse

interface NetworkDataSource {


    val downloadedVotesList: LiveData<VotesResponse>
    suspend fun fetchVotesList()

    val downloadedBalance: LiveData<BalanceResponse>
    suspend fun fetchBalance()

    suspend fun createVote(vote: Vote) : StatusResponse

    suspend fun postOpinion(opinion: Opinion) : StatusResponse
}