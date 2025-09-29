package com.softwarecleandevelopment.crypto_chains.dogecoin.domain.repository

import com.softwarecleandevelopment.core.crypto.models.AddressParams
import com.softwarecleandevelopment.core.common.utils.Resource

interface DogeCoinRepository {
    suspend fun generateAddress(params: AddressParams): Resource<String>
    suspend fun getDogeCoinBalance(address: String): Resource<Double>
    suspend fun estimateFee(address: String): Resource<Double>
}