package com.softwarecleandevelopment.crypto_chains.crypto_info.data.datasource

import android.os.Build
import androidx.annotation.RequiresApi
import com.softwarecleandevelopment.core.common.utils.Constants
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.crypto.models.AddressParams
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.model.CryptoInfo
import com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.EthDatasource
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.usecases.GetEthBalanceUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CryptoInfoDataSourceImpl @Inject constructor(
    private val api: CryptoApi,
    private val ethBalanceUseCase: GetEthBalanceUseCase,
    private val initialCryptos: List<CryptoInfo>
) : CryptoInfoDatasource {
    private val rpcUrl = Constants.rpcUrl

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getCryptoInfo(
        params: AddressParams,
    ): Flow<List<CryptoInfo>> {
        return flow {
            val updatedCryptos = fetchAndUpdateCryptoData(initialCryptos, params)
            emit(updatedCryptos)
        }
    }

    override suspend fun getBalance(
        symbol: String, userAddress: String
    ): Double {
        return when (symbol) {
            "ETH" -> ethBalanceUseCase.invoke(userAddress)
            "BTC" -> 0.0
            "DOGE" -> 0.0
            "USDT" -> 0.0
            else -> {
                TODO("Not yet implemented")
            }
        }
    }

    private suspend fun fetchAndUpdateCryptoData(
        cryptos: List<CryptoInfo>,
        params: AddressParams,
    ): List<CryptoInfo> {
        val cryptoIds = cryptos.joinToString(",") { it.id }
        val prices = api.getPrice(cryptoIds)

        return coroutineScope {
            cryptos.map { crypto ->
                val result = crypto.generator.invoke(params)
                val address = when (result) {
                    is Resource.Error -> {
                        "Error generating address"
                    }

                    is Resource.Loading -> {
                        "Loading address"
                    }

                    is Resource.Success<String> -> {
                        result.data
                    }
                }
                async {
                    updateCryptoInfo(crypto, prices, address)
                }
            }.awaitAll()
        }
    }

    private suspend fun updateCryptoInfo(
        crypto: CryptoInfo,
        prices: Map<String, Map<String, Double>>,
        userAddress: String
    ): CryptoInfo {
        val priceData = prices[crypto.id] ?: emptyMap()
        val balance = getBalance(crypto.symbol, userAddress)

        return crypto.copy(
            priceUsd = priceData["usd"] ?: 0.0,
            changePrecent = priceData["usd_24h_change"] ?: 0.0,
            balance = balance
        )
    }
}