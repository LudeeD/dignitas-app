package com.example.dig.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.dig.data.db.CacheDignitasDao
import com.example.dig.data.db.CacheVotesDao
import com.example.dig.data.db.converter.DateConverter
import com.example.dig.data.db.entity.Balance
import com.example.dig.data.db.entity.Opinion
import com.example.dig.data.db.entity.Vote
import com.example.dig.data.db.util.VoteDetail
import com.example.dig.data.db.util.VoteResume
import com.example.dig.data.network.NetworkDataSource
import com.example.dig.data.network.post.Transaction
import com.example.dig.data.network.response.BalanceResponse
import com.example.dig.data.network.response.StatusResponse
import com.example.dig.data.network.response.VotesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class DigRepositoryImpl(
    private val currentCacheVotesDao: CacheVotesDao,
    private val currentCacheBalanceDao:  CacheDignitasDao,
    private val networkDataSource: NetworkDataSource
    ) : DigRepository {

    init{
        networkDataSource.downloadedVotesList.observeForever{
                newVotes -> persistFetchedVotes(newVotes)
        }

        networkDataSource.downloadedBalance.observeForever{
            newBalance -> persistBalance(newBalance)
        }
    }

    override suspend fun getVoteDetailByPosition(str: Array<String>): LiveData<VoteDetail>{
        return withContext(Dispatchers.IO){
            return@withContext currentCacheVotesDao.getCachedVoteDetailByPosition(str[0], str[1])
        }
    }

    override suspend fun getBalance(): LiveData<Balance> {
        return withContext(Dispatchers.IO){
            initBalanceData()
            return@withContext currentCacheBalanceDao.getCachedBalance()
        }
    }

    private suspend fun initBalanceData() {
        networkDataSource.fetchBalance()
    }

    override suspend fun getVotesResume(): LiveData<List<VoteResume>> {
        return withContext(Dispatchers.IO) {
            initVoteData()
            return@withContext currentCacheVotesDao.getCachedVotesResume()
        }
    }

    private fun persistFetchedVotes(fetchedVotes : VotesResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentCacheVotesDao.insertAll(fetchedVotes.votes)
        }
    }

    private fun persistBalance(fetchedBalance: BalanceResponse){
        GlobalScope.launch(Dispatchers.IO){
            Log.v("PILAAAAAAAAAA", "persist Balance")
            currentCacheBalanceDao.addValue(Balance(Date(fetchedBalance.timestamp.toLong()), fetchedBalance.value))
        }
    }

    private suspend fun initVoteData(){
        // If there is Internet Always Fetch New Votes
        if(isFetchNeeded())
            //Maybe not the best way to do this
            currentCacheVotesDao.deleteCache()
            fetchVotes()
    }

    private suspend fun fetchVotes(){
        networkDataSource.fetchVotesList()
    }

    private fun isFetchNeeded() : Boolean{
        return true
    }

    override suspend fun postVote(vote: Vote): StatusResponse {
        return networkDataSource.createVote(vote)
    }

    override suspend fun postOpinion(opinion: Opinion) : StatusResponse {
       return networkDataSource.postOpinion(opinion)
    }
}