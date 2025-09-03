package com.softwarecleandevelopment.crypto_chains.ethereum.domain.repository.local

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.crypto.repository.BlockchainService
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface EthLocalRepository : BlockchainService {
    override fun generateAddress(): Resource<Flow<String?>>
    override suspend fun sendTransaction(from: String, to: String, amount: BigDecimal)
    override suspend fun getTransaction()
}