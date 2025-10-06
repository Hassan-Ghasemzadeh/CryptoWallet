package com.softwarecleandevelopment.crypto_chains.bitcoin.data.datasource

import com.softwarecleandevelopment.crypto_chains.crypto_info.data.model.BalanceResponse
import com.softwarecleandevelopment.crypto_chains.crypto_info.data.model.NetworkFeeInfo
import com.softwarecleandevelopment.crypto_chains.crypto_info.data.model.TransactionResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BitcoinApi {
    @GET("btc/main/addrs/{address}/balance")
    suspend fun getBitCoinBalance(@Path("address") address: String): BalanceResponse

    @GET("btc/main")
    suspend fun estimateFee(): NetworkFeeInfo

    @GET("btc/main/addrs/{address}/full")
    suspend fun getTransactions(@Path("address") address: String): TransactionResponse

}