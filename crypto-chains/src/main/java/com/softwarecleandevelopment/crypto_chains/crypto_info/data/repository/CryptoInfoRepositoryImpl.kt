package com.softwarecleandevelopment.crypto_chains.crypto_info.data.repository

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.safeFlowCall
import com.softwarecleandevelopment.crypto_chains.crypto_info.data.datasource.CryptoInfoDatasource
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.model.CryptoInfo
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.repository.CryptoInfoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CryptoInfoRepositoryImpl @Inject constructor(val datasource: CryptoInfoDatasource) :
    CryptoInfoRepository {
    override fun getCryptoInfo(userAddress: String): Resource<Flow<List<CryptoInfo>>> {
        return safeFlowCall { datasource.getCryptoInfo(userAddress = userAddress) }
    }

}