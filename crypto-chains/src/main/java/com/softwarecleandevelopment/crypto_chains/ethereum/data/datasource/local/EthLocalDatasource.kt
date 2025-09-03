package com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.local

import java.math.BigDecimal

interface EthLocalDatasource {
    suspend fun generateAddress()
    suspend fun sendTransaction(from: String, to: String, amount: BigDecimal)
    suspend fun getTransaction()
}