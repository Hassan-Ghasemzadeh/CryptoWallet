package com.softwarecleandevelopment.crypto_chains.crypto_info.data.datasource

import com.softwarecleandevelopment.core.crypto.models.AddressParams
import com.softwarecleandevelopment.core.crypto.models.CoinInfo
import com.softwarecleandevelopment.crypto_chains.crypto_info.data.model.Transaction
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.model.FeeEstimationParams
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.model.TransactionParams
import kotlinx.coroutines.flow.Flow

interface CryptoInfoDatasource {
    fun getCryptoInfo(
        params: AddressParams,
    ): Flow<List<CoinInfo>>

    suspend fun getBalance(
        symbol: String, userAddress: String
    ): Double

    suspend fun getFee(params: FeeEstimationParams): Double

    suspend fun getTransactions(params: TransactionParams): List<Transaction>
}