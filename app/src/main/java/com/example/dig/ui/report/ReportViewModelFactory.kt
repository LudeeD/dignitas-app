package com.example.dig.ui.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dig.data.repository.DigRepository

class ReportViewModelFactory(
    private val digRepository: DigRepository
) : ViewModelProvider.NewInstanceFactory()
{

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ReportViewModel(digRepository) as T
    }
}