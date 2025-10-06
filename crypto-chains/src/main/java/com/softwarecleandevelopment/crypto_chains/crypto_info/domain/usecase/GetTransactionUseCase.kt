package com.softwarecleandevelopment.crypto_chains.crypto_info.domain.usecase

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.crypto_chains.crypto_info.data.model.Transaction
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.model.TransactionParams
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.repository.CryptoInfoRepository
import javax.inject.Inject

class GetTransactionUseCase @Inject constructor(
    private val repository: CryptoInfoRepository,
) : UseCase<List<Transaction>, TransactionParams>() {
    override suspend fun invoke(params: TransactionParams): List<Transaction> {
        val result = repository.getTransactions(params)
        return when (result) {
            is Resource.Error -> emptyList()
            is Resource.Loading -> emptyList()
            is Resource.Success<List<Transaction>> -> result.data
        }
    }
}