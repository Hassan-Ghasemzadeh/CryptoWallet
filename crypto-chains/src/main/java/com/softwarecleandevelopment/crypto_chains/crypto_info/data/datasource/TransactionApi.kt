package com.softwarecleandevelopment.crypto_chains.crypto_info.data.datasource

import com.softwarecleandevelopment.crypto_chains.crypto_info.data.model.TransactionResponse
import retrofit2.http.GET
import retrofit2.http.Path


interface TransactionApi {
    @GET("{coin}/main/addrs/{address}")
    suspend fun getTransactions(
        @Path("coin") coin: String, @Path("address") address: String
    ): TransactionResponse
}