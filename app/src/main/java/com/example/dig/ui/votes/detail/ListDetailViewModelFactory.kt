package com.example.dig.ui.votes.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dig.data.repository.DigRepository

class ListDetailViewModelFactory(
    private val digRepository: DigRepository,
    private val vote_pos: Array<String>
) : ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListDetailViewModel(digRepository, vote_pos) as T
    }
}