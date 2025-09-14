package com.softwarecleandevelopment.crypto_chains.ethereum.domain.repository.local

import com.softwarecleandevelopment.core.common.utils.Resource
import kotlinx.coroutines.flow.Flow

interface EthLocalRepository {
    fun generateAddress(): Resource<Flow<String?>>
}