package com.softwarecleandevelopment.crypto_chains.dogecoin.data.datasource

import com.softwarecleandevelopment.core.crypto.models.AddressParams

interface DogeCoinDataSource {
    suspend fun generateAddress(params: AddressParams): String
}