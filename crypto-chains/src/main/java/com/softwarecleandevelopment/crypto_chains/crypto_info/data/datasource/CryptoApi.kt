package com.softwarecleandevelopment.crypto_chains.crypto_info.data.datasource

import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApi {
    @GET("simple/price")
    suspend fun getPrice(
        @Query("ids") ids: String,
        @Query("vs_currencies") currency: String = "usd",
        @Query("include_24hr_change") change: Boolean = true,
    ): Map<String, Map<String, Double>>
}