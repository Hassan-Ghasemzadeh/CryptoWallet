package com.softwarecleandevelopment.core.crypto

import java.math.BigDecimal

interface BlockchainService {
    suspend fun generateAddress()
    suspend fun getBalance(address: String): BigDecimal
}