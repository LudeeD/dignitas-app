package com.example.dig.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dig.data.db.entity.Balance
import com.example.dig.data.db.entity.Opinion
import com.example.dig.data.db.entity.Vote
import com.example.dig.data.network.response.BalanceResponse
import com.example.dig.data.network.response.StatusResponse
import com.example.dig.data.network.response.VotesResponse
import com.example.dig.internal.NoConnectivityException
import com.example.dig.sawtooth.DignitasHelper
import okhttp3.MediaType
import okhttp3.RequestBody

class NetworkDataSourceImpl(
    private val apiService: ApiService,
    private val dignitasHelper: DignitasHelper
) : NetworkDataSource {

    private val _downloadedVotesList = MutableLiveData<VotesResponse>()

    private val _downloadedBalance = MutableLiveData<BalanceResponse>()

    override val downloadedVotesList: LiveData<VotesResponse>
        get() = _downloadedVotesList

    override val downloadedBalance: LiveData<BalanceResponse>
        get() = _downloadedBalance

    override suspend fun fetchBalance() {
        try {
            val fetchedBalance = apiService.getBalance(dignitasHelper.retrieveWalletAddress()).await()
            _downloadedBalance.postValue(fetchedBalance)
        }catch (e: NoConnectivityException){
            Log.e("Connectivity", "No Internet", e)
        }
    }

    override suspend fun fetchVotesList() {
        try {
            val fetchedVotes = apiService.getVotes().await()
            _downloadedVotesList.postValue(fetchedVotes)
        }catch (e: NoConnectivityException){
            Log.e("Connectivity", "No Internet", e)
        }
    }

    override suspend fun createVote(vote: Vote) : StatusResponse {
        var status = StatusResponse("SOMETHING WENT WRONG");
        try {
            val transaction_bytes = dignitasHelper.createVote(vote)

            val body = RequestBody.create(MediaType.parse("application/octet-stream"), transaction_bytes)

            status = apiService.postTransaction(body).await()
        }catch (e: NoConnectivityException){
            Log.e("Connectivity", "No Internet", e)
        }

        return status
    }

    override suspend fun postOpinion(opinion: Opinion): StatusResponse {
        var status = StatusResponse("SOMETHING WENT WRONG");
        try {
            val transaction_bytes = dignitasHelper.createOpinion(opinion)
            val body = RequestBody.create(MediaType.parse("application/octet-stream"), transaction_bytes)
            status =  apiService.postTransaction(body).await()
        }catch (e: NoConnectivityException){
            Log.e("Connectivity", "No Internet", e)
        }
        return status
    }



}