package com.softwarecleandevelopment.crypto_chains.crypto_info.domain.usecase

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.model.CryptoInfo
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.repository.CryptoInfoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCryptoInfoUseCase @Inject constructor(val repository: CryptoInfoRepository) :
    UseCase<Flow<Resource<List<CryptoInfo>>>, String>() {
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun invoke(params: String): Flow<Resource<List<CryptoInfo>>> {
        return flow {
            emit(repository.getCryptoInfo(userAddress = params))
        }.flatMapLatest { result ->
            when (result) {
                is Resource.Success<Flow<List<CryptoInfo>>> -> {
                    result.data.map { cryptoInfoList -> Resource.Success(cryptoInfoList) }
                }

                is Resource.Error -> flowOf(Resource.Error(result.message))
                Resource.Loading -> flowOf(Resource.Loading)
            }
        }
    }
}