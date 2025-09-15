package com.softwarecleandevelopment.crypto_chains.ethereum.domain.usecases

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.SendResult
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.SendTokenEvent
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.repository.remote.EthRemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SendTokenUseCase @Inject constructor(private val repo: EthRemoteRepository) :
    UseCase<Flow<Resource<SendResult>>, SendTokenEvent>() {
    override suspend fun invoke(params: SendTokenEvent): Flow<Resource<SendResult>>  = flow {
        emit(
            Resource.Success(
                SendResult(
                    "",
                    null
                )
            )
        )
        val result = repo.send(
            params = params
        )
        emit(result)
    }.catch { e ->
        e.printStackTrace()
        emit(Resource.Error(e.message ?: "An unexpected error occurred"))
    }
        .flowOn(Dispatchers.IO)

}