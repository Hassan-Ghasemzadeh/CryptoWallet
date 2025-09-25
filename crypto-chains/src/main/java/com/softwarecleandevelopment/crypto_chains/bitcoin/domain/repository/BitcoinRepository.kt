package com.softwarecleandevelopment.crypto_chains.bitcoin.domain.repository

import com.softwarecleandevelopment.core.common.model.AddressParams
import com.softwarecleandevelopment.core.common.utils.Resource

interface BitcoinRepository {
    suspend fun generateBitcoinAddress(params: AddressParams): Resource<String>
}