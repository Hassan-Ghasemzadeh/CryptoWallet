package com.softwarecleandevelopment.core.crypto.repository

import com.softwarecleandevelopment.core.common.utils.Resource


interface BlockchainService<in P> {
    suspend fun generateAddress(params: P): Resource<String>
}