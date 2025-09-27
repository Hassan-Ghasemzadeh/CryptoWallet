package com.softwarecleandevelopment.crypto_chains.dogecoin.domian.usecase

import com.softwarecleandevelopment.core.common.utils.AddressGenerator
import com.softwarecleandevelopment.core.crypto.models.AddressParams
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.crypto_chains.dogecoin.domian.repository.DogeCoinRepository
import javax.inject.Inject

class GenerateDogeCoinAddressUseCase @Inject constructor(private val repository: DogeCoinRepository) :
    UseCase<Resource<String>, AddressParams>(), AddressGenerator {
    override suspend fun invoke(params: AddressParams): Resource<String> {
        return repository.generateAddress(params)
    }
}