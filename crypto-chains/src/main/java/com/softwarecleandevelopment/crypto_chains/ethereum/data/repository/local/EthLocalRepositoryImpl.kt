package com.softwarecleandevelopment.crypto_chains.ethereum.data.repository.local

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.safeFlowCall
import com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.local.EthLocalDatasource
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.repository.local.EthLocalRepository
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import javax.inject.Inject

class EthLocalRepositoryImpl @Inject constructor(
    private val datasource: EthLocalDatasource,
) : EthLocalRepository {
    override fun generateAddress(): Resource<Flow<String?>> {
        return safeFlowCall { datasource.generateAddress() }
    }
}