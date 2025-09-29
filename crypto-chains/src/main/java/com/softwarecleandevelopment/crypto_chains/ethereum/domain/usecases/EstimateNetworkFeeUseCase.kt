package com.softwarecleandevelopment.crypto_chains.ethereum.domain.usecases

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.repository.EthRepository
import javax.inject.Inject

class EstimateNetworkFeeUseCase @Inject constructor(val repository: EthRepository) :
    UseCase<Resource<Double>, String?>() {
    override suspend fun invoke(params: String?): Resource<Double> {
        return repository.estimateNetworkFee(params)
    }
}