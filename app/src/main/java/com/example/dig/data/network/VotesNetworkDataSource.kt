package com.example.dig.data.network

import androidx.lifecycle.LiveData
import com.example.dig.data.network.response.VotesResponse

interface VotesNetworkDataSource {

    val downloadedVotes: LiveData<VotesResponse>

    suspend fun fetchVotes()
}