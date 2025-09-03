package com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.remote


interface EthCryptoRemoteDatasource {
    suspend fun getPrice(
        ids: String,
        change: Boolean = true,
    ): Map<String, Map<String, Double>>

    suspend fun getBalance(symbol: String, userAddress: String): Double

    suspend fun getEthBalance(rpcUrl: String, address: String): Double
}