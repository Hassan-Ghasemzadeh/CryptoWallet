package com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.local

import java.math.BigDecimal

class EthLocalDatasourceImpl : EthLocalDatasource {
    override suspend fun generateAddress() {
        TODO("Not yet implemented")
    }

    override suspend fun sendTransaction(
        from: String,
        to: String,
        amount: BigDecimal
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun getTransaction() {
        TODO("Not yet implemented")
    }
}