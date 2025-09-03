package com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.local

import com.softwarecleandevelopment.core.database.room.dao.WalletDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.math.BigDecimal
import javax.inject.Inject

class EthLocalDatasourceImpl @Inject constructor(
    private val dao: WalletDao
) : EthLocalDatasource {
    override suspend fun generateAddress(): Flow<String?> {
        return dao.getActiveWallet().map { it?.address }
    }

    override suspend fun sendTransaction(
        from: String, to: String, amount: BigDecimal
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun getTransaction() {
        TODO("Not yet implemented")
    }
}