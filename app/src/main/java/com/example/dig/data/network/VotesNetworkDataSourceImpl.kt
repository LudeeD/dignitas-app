package com.example.dig.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dig.data.network.response.VotesResponse
import com.example.dig.internal.NoConnectivityException

class VotesNetworkDataSourceImpl(
    private val apiService: ApiService
) : VotesNetworkDataSource {

    private val _downloadedVote = MutableLiveData<VotesResponse>()

    override val downloadedVotes: LiveData<VotesResponse>
        get() = _downloadedVote

    override suspend fun fetchVotes() {
        try{
            val fetchedVotes = apiService.getVotes().await()
            _downloadedVote.postValue(fetchedVotes)
        } catch(e : NoConnectivityException){
            Log.e("Connectivity", "No Internet", e)
        }
    }
}