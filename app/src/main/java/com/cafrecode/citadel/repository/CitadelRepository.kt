package com.cafrecode.citadel.repository

import com.cafrecode.citadel.AppExecutors
import com.cafrecode.citadel.api.CitadelService
import javax.inject.Inject

class CitadelRepository @Inject constructor(
    private val service: CitadelService,
    private val appExecutors: AppExecutors
) {
    fun accountExists(address: String) = service.accountExists(address)
}