package com.softwarecleandevelopment.crypto_chains.crypto_info.data.repository

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.safeCall
import com.softwarecleandevelopment.core.common.utils.safeFlowCall
import com.softwarecleandevelopment.core.crypto.models.AddressParams
import com.softwarecleandevelopment.crypto_chains.crypto_info.data.datasource.CryptoInfoDatasource
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.model.CoinInfo
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.model.FeeEstimationParams
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.repository.CryptoInfoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CryptoInfoRepositoryImpl @Inject constructor(val datasource: CryptoInfoDatasource) :
    CryptoInfoRepository {
    override fun getCryptoInfo(
        params: AddressParams,
    ): Resource<Flow<List<CoinInfo>>> {
        return safeFlowCall { datasource.getCryptoInfo(params = params) }
    }

    override suspend fun getFee(params: FeeEstimationParams): Resource<Double> {
        return safeCall { datasource.getFee(params) }
    }

}