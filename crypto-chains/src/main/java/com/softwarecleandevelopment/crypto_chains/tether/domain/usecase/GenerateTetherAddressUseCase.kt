package com.softwarecleandevelopment.crypto_chains.tether.domain.usecase

import com.softwarecleandevelopment.core.common.model.AddressParams
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.crypto_chains.tether.domain.repository.TetherRepository
import javax.inject.Inject

class GenerateTetherAddressUseCase @Inject constructor(
    private val repository: TetherRepository
) : UseCase<Resource<String>, AddressParams>() {
    override suspend fun invoke(params: AddressParams): Resource<String> {
        return repository.generateAddress(params)
    }
}