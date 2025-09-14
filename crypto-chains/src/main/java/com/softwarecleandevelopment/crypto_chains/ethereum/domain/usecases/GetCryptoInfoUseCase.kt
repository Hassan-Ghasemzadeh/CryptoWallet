package com.softwarecleandevelopment.crypto_chains.ethereum.domain.usecases

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.CryptoInfo
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.GetCryptoInfoEvent
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.repository.remote.EthRemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCryptoInfoUseCase @Inject constructor(val repository: EthRemoteRepository) :
    UseCase<Resource<Flow<List<CryptoInfo>>>, GetCryptoInfoEvent>() {
    override suspend fun invoke(params: GetCryptoInfoEvent): Resource<Flow<List<CryptoInfo>>> {
        return repository.getCryptoInfo(cryptos = params.cryptos, userAddress = params.address)
    }
}