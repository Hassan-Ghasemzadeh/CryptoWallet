package com.softwarecleandevelopment.crypto_chains.bitcoin.domain.repository

import com.softwarecleandevelopment.core.crypto.models.AddressParams
import com.softwarecleandevelopment.core.common.utils.Resource

interface BitcoinRepository {
    suspend fun generateAddress(params: AddressParams): Resource<String>
    suspend fun getBitCoinBalance(address: String): Resource<Double>
    suspend fun estimateFee(address: String): Resource<Double>
}