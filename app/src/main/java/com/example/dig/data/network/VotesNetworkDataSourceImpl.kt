package com.example.dig.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dig.data.db.entity.Location
import com.example.dig.data.db.entity.Vote
import com.example.dig.data.network.post.Transaction
import com.example.dig.data.network.response.StatusResponse
import com.example.dig.data.network.response.VotesResponse
import com.example.dig.internal.NoConnectivityException
import com.example.dig.sawtooth.DignitasHelper
import kotlinx.coroutines.Deferred

class VotesNetworkDataSourceImpl(
    private val apiService: ApiService,
    private val dignitasHelper : DignitasHelper
) : VotesNetworkDataSource {

    override suspend fun postVote(vote : Vote) : StatusResponse {

        val transaction = dignitasHelper.createVote(vote)
        Log.v("pila", transaction.toString());
        return  apiService.postVotes(transaction).await()
    }

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

    override suspend fun fetchGeoId(location: Location): String {
        return apiService.fetchGeoId(location).await()
    }
}