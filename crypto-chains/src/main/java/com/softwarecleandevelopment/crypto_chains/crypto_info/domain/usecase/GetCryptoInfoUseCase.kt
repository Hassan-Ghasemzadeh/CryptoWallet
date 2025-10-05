package com.softwarecleandevelopment.crypto_chains.crypto_info.domain.usecase

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.core.crypto.models.AddressParams
import com.softwarecleandevelopment.core.crypto.models.CoinInfo
import com.softwarecleandevelopment.core.database.cache_datastore.CacheDataStore
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.repository.CryptoInfoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCryptoInfoUseCase @Inject constructor(
    val repository: CryptoInfoRepository,
) :
    UseCase<Flow<Resource<List<CoinInfo>>>, AddressParams>() {
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun invoke(params: AddressParams): Flow<Resource<List<CoinInfo>>> {
        return flow {
            emit(repository.getCryptoInfo(params))
        }.flatMapLatest { result ->
            when (result) {
                is Resource.Success<Flow<List<CoinInfo>>> -> {
                    result.data.map { cryptoInfoList -> Resource.Success(cryptoInfoList) }
                }

                is Resource.Error -> flowOf(Resource.Error(result.message))
                Resource.Loading -> flowOf(Resource.Loading)
            }
        }
    }
}