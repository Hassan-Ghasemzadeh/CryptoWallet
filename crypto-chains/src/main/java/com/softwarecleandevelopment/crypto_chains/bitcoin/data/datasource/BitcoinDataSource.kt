package com.softwarecleandevelopment.crypto_chains.bitcoin.data.datasource

import com.softwarecleandevelopment.core.crypto.models.AddressParams


interface BitcoinDataSource {
    suspend fun generateAddress(params: AddressParams): String
    suspend fun getBitcoinBalance(address: String): Double

}