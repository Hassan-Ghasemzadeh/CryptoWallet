package com.softwarecleandevelopment.crypto_chains.dogecoin.domian.repository

import com.softwarecleandevelopment.core.common.model.AddressParams
import com.softwarecleandevelopment.core.common.utils.Resource

interface DogeCoinRepository {
    suspend fun generateAddress(params: AddressParams): Resource<String>
}