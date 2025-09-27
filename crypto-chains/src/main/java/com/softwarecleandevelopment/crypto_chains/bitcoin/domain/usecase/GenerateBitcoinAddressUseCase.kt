package com.softwarecleandevelopment.crypto_chains.bitcoin.domain.usecase

import com.softwarecleandevelopment.core.common.utils.AddressGenerator
import com.softwarecleandevelopment.core.crypto.models.AddressParams
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.crypto_chains.bitcoin.domain.repository.BitcoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GenerateBitcoinAddressUseCase @Inject constructor(
    private val repository: BitcoinRepository,
) : UseCase<Resource<String>, AddressParams>(), AddressGenerator {
    override suspend fun invoke(params: AddressParams): Resource<String> {
        return repository.generateAddress(params)
    }
}