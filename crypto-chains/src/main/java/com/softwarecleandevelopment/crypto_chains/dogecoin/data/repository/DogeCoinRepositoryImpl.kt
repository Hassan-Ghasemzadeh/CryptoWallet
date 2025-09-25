package com.softwarecleandevelopment.crypto_chains.dogecoin.data.repository

import com.softwarecleandevelopment.core.crypto.models.AddressParams
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.safeCall
import com.softwarecleandevelopment.crypto_chains.dogecoin.data.datasource.DogeCoinDataSource
import com.softwarecleandevelopment.crypto_chains.dogecoin.domian.repository.DogeCoinRepository
import javax.inject.Inject

class DogeCoinRepositoryImpl @Inject constructor(private val dataSource: DogeCoinDataSource) :
    DogeCoinRepository {
    override suspend fun generateAddress(
        params: AddressParams,
    ): Resource<String> {
        return safeCall { dataSource.generateAddress(params) }
    }
}