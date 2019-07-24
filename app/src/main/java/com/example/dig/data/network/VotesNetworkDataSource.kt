package com.example.dig.data.network

import androidx.lifecycle.LiveData
import com.example.dig.data.db.entity.Location
import com.example.dig.data.db.entity.Vote
import com.example.dig.data.network.post.Transaction
import com.example.dig.data.network.response.StatusResponse
import com.example.dig.data.network.response.VotesResponse

interface VotesNetworkDataSource {

    val downloadedVotes: LiveData<VotesResponse>

    suspend fun fetchVotes()

    suspend fun postVote(vote: Vote): StatusResponse

    suspend fun fetchGeoId(location: Location): String

}