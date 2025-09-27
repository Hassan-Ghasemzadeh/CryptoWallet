package com.softwarecleandevelopment.crypto_chains.ethereum.domain.usecases

import com.softwarecleandevelopment.core.common.utils.AddressGenerator
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.core.crypto.models.AddressParams
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.repository.EthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class GenerateEthAddressUseCase @Inject constructor(
    private val repository: EthRepository,
) : UseCase<Resource<Flow<String?>>, Unit>(), AddressGenerator {
    override suspend fun invoke(params: Unit): Resource<Flow<String?>> {
        return repository.generateAddress()
    }

    override suspend fun invoke(params: AddressParams): Resource<String> {
        val result = repository.generateAddress()
        if (result is Resource.Success) {
            val address = result.data.firstOrNull()
            return Resource.Success(address ?: "")
        } else {
            return Resource.Error("Unable to generate address,Please check the input params")
        }
    }
}