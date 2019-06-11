package com.example.dig.ui.votes.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dig.data.repository.DigRepository

class ListViewModelFactory(
    private val digRepository: DigRepository
) : ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListViewModel(digRepository) as T
    }
}