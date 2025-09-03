package com.softwarecleandevelopment.crypto_chains.ethereum.domain.repository.local

import com.softwarecleandevelopment.core.crypto.repository.BlockchainService
import java.math.BigDecimal

interface EthLocalRepository : BlockchainService {
    override suspend fun generateAddress()
    override suspend fun sendTransaction(from: String, to: String, amount: BigDecimal)
    override suspend fun getTransaction()
}