package com.example.dig.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.example.dig.data.db.entity.Vote
import com.example.dig.data.network.post.Transaction
import com.example.dig.data.network.response.StatusResponse
import com.example.dig.data.repository.DigRepository
import com.example.dig.internal.lazyDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReportViewModel (
    private val digRepository: DigRepository
) : ViewModel() {

    val status : MutableLiveData<String> = MutableLiveData()

    suspend fun postVote(vote: Vote){
        withContext(Dispatchers.Main) {
            status.value = digRepository.postVote(vote).status
        }
    }



}
