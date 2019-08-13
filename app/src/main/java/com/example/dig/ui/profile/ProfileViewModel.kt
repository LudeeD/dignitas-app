package com.example.dig.ui.profile

import androidx.lifecycle.ViewModel;
import com.example.dig.data.repository.DigRepository
import com.example.dig.internal.lazyDeferred

class ProfileViewModel(
    private val digRepository: DigRepository
) : ViewModel() {

    val balance by lazyDeferred {
        digRepository.getBalance()
    }

}
