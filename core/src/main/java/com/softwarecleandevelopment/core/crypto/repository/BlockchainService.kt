package com.softwarecleandevelopment.core.crypto.repository

import com.softwarecleandevelopment.core.common.utils.Resource
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface BlockchainService {
    fun generateAddress(): Resource<Flow<String?>>
    suspend fun sendTransaction(from: String, to: String, amount: BigDecimal)
    suspend fun getTransaction()
}