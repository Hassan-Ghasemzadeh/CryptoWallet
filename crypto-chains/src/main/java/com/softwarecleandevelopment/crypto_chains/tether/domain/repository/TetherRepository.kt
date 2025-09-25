package com.softwarecleandevelopment.crypto_chains.tether.domain.repository

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.model.AddressParams

interface TetherRepository {
    suspend fun generateAddress(
        params: AddressParams
    ): Resource<String>
}