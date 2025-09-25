package com.softwarecleandevelopment.crypto_chains.tether.data.repository

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.safeCall
import com.softwarecleandevelopment.crypto_chains.tether.data.datasource.TetherDataSource
import com.softwarecleandevelopment.core.common.model.AddressParams
import com.softwarecleandevelopment.crypto_chains.tether.domain.repository.TetherRepository
import javax.inject.Inject

class TetherRepositoryImpl @Inject constructor(
    private val dataSource: TetherDataSource
) : TetherRepository {
    override suspend fun generateAddress(
        params: AddressParams
    ): Resource<String> {
        return safeCall { dataSource.generateAddress(params) }
    }
}