package com.softwarecleandevelopment.crypto_chains.crypto_info.domain.usecase

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.model.CryptoInfo
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.repository.CryptoInfoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCryptoInfoUseCase @Inject constructor(val repository: CryptoInfoRepository) :
    UseCase<Resource<Flow<List<CryptoInfo>>>, String>() {
    override suspend fun invoke(params: String): Resource<Flow<List<CryptoInfo>>> {
        return repository.getCryptoInfo(userAddress = params)
    }
}