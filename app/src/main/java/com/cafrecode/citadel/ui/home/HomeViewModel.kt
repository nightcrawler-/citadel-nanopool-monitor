package com.cafrecode.citadel.ui.home

import androidx.lifecycle.ViewModel
import com.cafrecode.citadel.repository.CitadelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: CitadelRepository) :
    ViewModel() {

    fun accountExists(account: String) = repo.accountExists(account)
}