package com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface EthCryptoApi {
    @GET("simple/price")
    suspend fun getPrice(
        @Query("ids") ids: String,
        @Query("vs_currencies") currency: String = "usd",
        @Query("include_24hr_change") change: Boolean = true,
    ): Map<String, Map<String, Double>>
}