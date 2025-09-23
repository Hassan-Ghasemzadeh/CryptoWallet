package com.softwarecleandevelopment.crypto_chains.tether.data.repository

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.safeCall
import com.softwarecleandevelopment.crypto_chains.tether.data.datasource.TetherDataSource
import com.softwarecleandevelopment.crypto_chains.tether.domain.repository.TetherRepository
import javax.inject.Inject

class TetherRepositoryImpl @Inject constructor(
    private val dataSource: TetherDataSource
) : TetherRepository {
    override suspend fun generateAddress(
        mnemonic: String,
        passPhrase: String?
    ): Resource<String> {
        return safeCall { dataSource.generateAddress(mnemonic, passPhrase) }
    }
}