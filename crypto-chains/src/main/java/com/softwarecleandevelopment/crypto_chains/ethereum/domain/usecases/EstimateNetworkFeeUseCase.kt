package com.softwarecleandevelopment.crypto_chains.ethereum.domain.usecases

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.repository.remote.EthRemoteRepository
import java.math.BigInteger
import javax.inject.Inject

class EstimateNetworkFeeUseCase @Inject constructor(val repository: EthRemoteRepository) :
    UseCase<Resource<Pair<BigInteger /*gasPrice*/, BigInteger /*gasLimit*/>>, String?>() {
    override suspend fun invoke(params: String?): Resource<Pair<BigInteger, BigInteger>> {
        return repository.estimateNetworkFee(params)
    }
}