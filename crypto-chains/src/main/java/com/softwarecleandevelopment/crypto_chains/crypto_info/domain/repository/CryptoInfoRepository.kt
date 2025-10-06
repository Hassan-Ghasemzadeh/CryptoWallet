package com.softwarecleandevelopment.crypto_chains.crypto_info.domain.repository

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.crypto.models.AddressParams
import com.softwarecleandevelopment.core.crypto.models.CoinInfo
import com.softwarecleandevelopment.crypto_chains.crypto_info.data.model.Transaction
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.model.FeeEstimationParams
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.model.TransactionParams
import kotlinx.coroutines.flow.Flow

interface CryptoInfoRepository {
    fun getCryptoInfo(
        params: AddressParams,
    ): Resource<Flow<List<CoinInfo>>>

    suspend fun getFee(params: FeeEstimationParams): Resource<Double>

    suspend fun getTransactions(params: TransactionParams): Resource<List<Transaction>>

}