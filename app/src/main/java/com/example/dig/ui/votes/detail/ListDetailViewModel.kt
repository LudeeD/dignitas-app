package com.example.dig.ui.votes.detail

import androidx.lifecycle.ViewModel;
import com.example.dig.data.db.entity.Opinion
import com.example.dig.data.repository.DigRepository
import com.example.dig.internal.lazyDeferred

class ListDetailViewModel(
    private val digRepository: DigRepository,
    private val vote_pos : Array<String>
) : ViewModel() {

    val vote by lazyDeferred{
        digRepository.getVoteDetailByPosition(vote_pos)
    }

   suspend fun postOpinion(op: Opinion){
       digRepository.postOpinion(op)
   }

}
