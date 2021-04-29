package com.cafrecode.citadel.api

import androidx.lifecycle.LiveData
import com.cafrecode.citadel.vo.responses.core.AccountAvailable
import com.cafrecode.citadel.vo.responses.core.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CitadelService {

    @GET("xmr/accountexist/{address}")
    fun accountExists(@Path("address") address: String): LiveData<ApiResponse<AccountAvailable>>
}