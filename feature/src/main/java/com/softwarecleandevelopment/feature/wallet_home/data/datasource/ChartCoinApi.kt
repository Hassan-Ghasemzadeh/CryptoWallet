package com.softwarecleandevelopment.feature.wallet_home.data.datasource

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ChartCoinApi {
    @GET("coins/{id}")
    suspend fun getChartDetail(
        @Path("id") id: String,
        @Query("localization") localization: Boolean = false,
        @Query("tickers") tickers: Boolean = false,
        @Query("community_data") communityData: Boolean = false,
        @Query("developer_data") developerData: Boolean = false,
        @Query("sparkline") sparkline: Boolean = true,
    ): ChartCoinDto
}