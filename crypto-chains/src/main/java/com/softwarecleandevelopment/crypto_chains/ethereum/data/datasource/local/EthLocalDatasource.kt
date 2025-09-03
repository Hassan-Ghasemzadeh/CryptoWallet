package com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.local

import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface EthLocalDatasource {
    suspend fun generateAddress() : Flow<String?>
    suspend fun sendTransaction(from: String, to: String, amount: BigDecimal)
    suspend fun getTransaction()
}