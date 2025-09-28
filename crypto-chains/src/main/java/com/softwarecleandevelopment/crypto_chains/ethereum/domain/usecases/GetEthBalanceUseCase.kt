package com.softwarecleandevelopment.crypto_chains.ethereum.domain.usecases

import com.softwarecleandevelopment.core.common.utils.BalanceUseCase
import com.softwarecleandevelopment.core.common.utils.Constants
import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.EthDatasource
import javax.inject.Inject

class GetEthBalanceUseCase @Inject constructor(private val datasource: EthDatasource) :
    UseCase<Double, String>(), BalanceUseCase {
    override suspend fun invoke(params: String): Double {
        return datasource.getEthBalance(Constants.rpcUrl, params)
    }
}