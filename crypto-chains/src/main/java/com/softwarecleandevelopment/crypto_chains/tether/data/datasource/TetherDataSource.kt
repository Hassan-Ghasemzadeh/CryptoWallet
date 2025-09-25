package com.softwarecleandevelopment.crypto_chains.tether.data.datasource

import com.softwarecleandevelopment.core.common.model.AddressParams

interface TetherDataSource {
    suspend fun generateAddress(params: AddressParams): String
}