package com.softwarecleandevelopment.crypto_chains.tether.domain.usecase

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.crypto_chains.tether.domain.repository.TetherRepository
import javax.inject.Inject

class GenerateTetherAddressUseCase @Inject constructor(
    private val repository: TetherRepository
) : UseCase<Resource<String>, String>() {
    override suspend fun invoke(params: String): Resource<String> {
        return repository.generateAddress(params, null)
    }
}