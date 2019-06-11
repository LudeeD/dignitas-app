package com.example.dig.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.dig.data.db.CacheVotesDao
import com.example.dig.data.db.entity.Opinion
import com.example.dig.data.db.entity.Vote
import com.example.dig.data.db.util.VoteDetail
import com.example.dig.data.db.util.VoteResume
import com.example.dig.data.network.OpinionData
import com.example.dig.data.network.VotesNetworkDataSource
import com.example.dig.data.network.response.VotesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DigRepositoryImpl(
    private val currentCacheVotesDao: CacheVotesDao,
    private val networkDataSource: VotesNetworkDataSource,
    private val networkDataSourceOpinion: OpinionData
) : DigRepository {

    init{
        networkDataSource.downloadedVotes.observeForever{
                newVotes -> persistFetchedVotes(newVotes)
        }
    }

    override suspend fun postOpinion(opinion: Opinion) {
       networkDataSourceOpinion.postOpinions(opinion)
    }


    override suspend fun getVoteDetailById(id_vote: Int): LiveData<VoteDetail> {
        return withContext(Dispatchers.IO){
            // Not Necessary
            //initVoteData()
            return@withContext currentCacheVotesDao.getCachedVoteDetailById(id_vote)
        }
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

    private suspend fun initVoteData(){
        // If there is Internet Always Fetch New Votes
        if(isFetchNeeded())
            fetchVotes()
    }

    private suspend fun fetchVotes(){
        networkDataSource.fetchVotes()
    }

    private fun isFetchNeeded() : Boolean{
        return true
    }
}