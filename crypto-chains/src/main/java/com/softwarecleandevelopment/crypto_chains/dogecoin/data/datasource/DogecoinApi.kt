package com.softwarecleandevelopment.crypto_chains.dogecoin.data.datasource

import com.softwarecleandevelopment.crypto_chains.crypto_info.data.model.BalanceResponse
import com.softwarecleandevelopment.crypto_chains.crypto_info.data.model.NetworkFeeInfo
import retrofit2.http.GET
import retrofit2.http.Path


interface DogecoinApi {
    @GET("doge/main/addrs/{address}/balance")
    suspend fun getDogeCoinBalance(@Path("address") address: String): BalanceResponse

    @GET("doge/main/")
    suspend fun estimateFee(address: String): NetworkFeeInfo
}