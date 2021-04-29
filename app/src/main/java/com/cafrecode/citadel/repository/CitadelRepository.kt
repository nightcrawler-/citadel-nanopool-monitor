package com.cafrecode.citadel.repository

import androidx.lifecycle.LiveData
import com.cafrecode.citadel.AppExecutors
import com.cafrecode.citadel.api.CitadelService
import com.cafrecode.citadel.vo.responses.core.ApiResponse
import javax.inject.Inject

class CitadelRepository @Inject constructor(
    private val service: CitadelService,
    private val appExecutors: AppExecutors
) {
    fun accountExists(address: String): LiveData<ApiResponse<String>> {
        return service.accountExists(address)
    }
}