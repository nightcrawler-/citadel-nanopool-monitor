package com.cafrecode.citadel.ui.home

import androidx.lifecycle.ViewModel
import com.cafrecode.citadel.repository.CitadelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: CitadelRepository) :
    ViewModel() {

    fun accountExists(account: String) = repo.accountExists(account)

    //TODO: Use these things the correct way, understand how to viewmodels!

    fun accountBalance(address: String) = repo.accountBalance(address)

    fun currentHashrate(address: String) = repo.currentHashrate(address)

    fun averageHashrate(address: String, hours: Int) = repo.averageHashrate(address, hours)

    fun generalInfo(address: String) = repo.generalInfo(address)
}