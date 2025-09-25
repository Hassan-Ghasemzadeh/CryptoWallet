package com.softwarecleandevelopment.crypto_chains.bitcoin.data.repository

import com.softwarecleandevelopment.core.crypto.models.AddressParams
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.safeCall
import com.softwarecleandevelopment.crypto_chains.bitcoin.data.datasource.BitcoinDataSource
import com.softwarecleandevelopment.crypto_chains.bitcoin.domain.repository.BitcoinRepository
import javax.inject.Inject

class BitcoinRepositoryImpl @Inject constructor(private val dataSource: BitcoinDataSource) :
    BitcoinRepository {
    override suspend fun generateBitcoinAddress(
        params: AddressParams
    ): Resource<String> {
        return safeCall { dataSource.generateAddress(params) }
    }
}