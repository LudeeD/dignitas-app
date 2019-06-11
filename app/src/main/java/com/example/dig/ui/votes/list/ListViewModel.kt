package com.example.dig.ui.votes.list

import android.util.Log
import androidx.lifecycle.ViewModel;
import com.example.dig.data.repository.DigRepository
import com.example.dig.internal.lazyDeferred

//https://www.youtube.com/watch?v=DwnloROxaKg
class ListViewModel(
    private val digRepository: DigRepository
) : ViewModel() {

    val votes by lazyDeferred() {
        digRepository.getVotesResume()
    }
}
