package com.softwarecleandevelopment.crypto_chains.bitcoin.domain.usecase

import com.softwarecleandevelopment.core.crypto.models.AddressParams
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.crypto_chains.bitcoin.domain.repository.BitcoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GenerateBitcoinAddressUseCase @Inject constructor(
    private val repository: BitcoinRepository,
) : UseCase<Flow<Resource<String>>, AddressParams>() {
    override suspend fun invoke(params: AddressParams): Flow<Resource<String>> = flow {
        val result = repository.generateBitcoinAddress(params)
        when (result) {
            is Resource.Success -> emit(Resource.Success(result.data))
            is Resource.Error -> emit(Resource.Error(result.message, result.throwable))
            is Resource.Loading -> emit(Resource.Loading)
        }
    }
}