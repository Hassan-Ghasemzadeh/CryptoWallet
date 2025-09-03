package com.softwarecleandevelopment.core.crypto.repository

import java.math.BigDecimal

interface BlockchainService {
    suspend fun generateAddress()
    suspend fun getBalance(address: String): BigDecimal
    suspend fun sendTransaction(from: String, to: String, amount: BigDecimal)
    suspend fun getTransaction()
}