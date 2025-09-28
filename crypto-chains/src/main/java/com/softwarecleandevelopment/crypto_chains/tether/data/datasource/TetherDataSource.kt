package com.softwarecleandevelopment.crypto_chains.tether.data.datasource

import com.softwarecleandevelopment.core.crypto.models.AddressParams

interface TetherDataSource {
    suspend fun generateAddress(params: AddressParams): String
    suspend fun getTetherBalance(address: String): String
}