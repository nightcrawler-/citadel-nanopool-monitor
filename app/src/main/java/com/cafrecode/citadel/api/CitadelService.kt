package com.cafrecode.citadel.api

import androidx.lifecycle.LiveData
import com.cafrecode.citadel.vo.responses.core.ApiResponse
import com.cafrecode.citadel.vo.responses.core.GeneralResponse
import com.cafrecode.citadel.vo.responses.core.GenericResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CitadelService {

    @GET("accountexist/{address}")
    fun accountExists(@Path("address") address: String): LiveData<ApiResponse<GenericResponse>>

    @GET("balance/{address}")
    fun accountBalance(@Path("address") address: String): LiveData<ApiResponse<GenericResponse>>

    @GET("avghashratelimited/{address}/{hours}")
    fun averageHashrate(
        @Path("address") address: String,
        @Path("hours") hours: Int
    ): LiveData<ApiResponse<GenericResponse>>

    @GET("hashrate/{address}")
    fun currentHashrate(@Path("address") address: String): LiveData<ApiResponse<GenericResponse>>

    @GET("user/{address}")
    fun generalInfo(@Path("address") address: String): LiveData<ApiResponse<GeneralResponse>>

    @GET("prices")
    fun prices(): LiveData<ApiResponse<GeneralResponse>>
}