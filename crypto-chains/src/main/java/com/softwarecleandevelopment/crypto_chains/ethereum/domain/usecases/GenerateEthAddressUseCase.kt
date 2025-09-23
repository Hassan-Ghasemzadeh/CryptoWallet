package com.softwarecleandevelopment.crypto_chains.ethereum.domain.usecases

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.repository.EthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GenerateEthAddressUseCase @Inject constructor(
    private val repository: EthRepository,
) : UseCase<Resource<Flow<String?>>, Unit>() {
    override suspend fun invoke(params: Unit): Resource<Flow<String?>> {
        return repository.generateAddress()
    }
}