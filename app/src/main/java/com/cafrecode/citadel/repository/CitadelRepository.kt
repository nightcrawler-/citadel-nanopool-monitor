package com.cafrecode.citadel.repository

import com.cafrecode.citadel.AppExecutors
import com.cafrecode.citadel.api.CitadelService
import javax.inject.Inject

class CitadelRepository @Inject constructor(
    private val service: CitadelService,
    private val appExecutors: AppExecutors
) {
    fun accountExists(address: String) = service.accountExists(address)

    fun accountBalance(address: String) = service.accountBalance(address)

    fun currentHashrate(address: String) = service.currentHashrate(address)

    fun averageHashrate(address: String, hours: Int) = service.averageHashrate(address, hours)

    fun generalInfo(address: String) = service.generalInfo(address)

    fun prices() = service.prices()

    fun account(address: String) = service.account(address)
}