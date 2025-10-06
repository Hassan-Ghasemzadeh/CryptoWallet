package com.softwarecleandevelopment.crypto_chains.crypto_info.data.datasource

import android.os.Build
import androidx.annotation.RequiresApi
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.core.crypto.models.AddressParams
import com.softwarecleandevelopment.crypto_chains.crypto_info.data.utils.BalanceManager
import com.softwarecleandevelopment.core.crypto.models.CoinInfo
import com.softwarecleandevelopment.core.database.cache_datastore.CacheDataStore
import com.softwarecleandevelopment.crypto_chains.crypto_info.data.model.Transaction
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.model.FeeEstimationParams
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.model.TransactionParams
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CryptoInfoDataSourceImpl @Inject constructor(
    private val cryptoApi: CryptoApi,
    private val transactionApi: TransactionApi,
    private val manager: BalanceManager,
    private val initialCryptos: List<CoinInfo>,
    private val estimators: Map<String, @JvmSuppressWildcards UseCase<Double, String>>,
    private val cache: CacheDataStore,
) : CryptoInfoDatasource {
    private val cacheTTl: Long = 60_000L

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getCryptoInfo(
        params: AddressParams,
    ): Flow<List<CoinInfo>> {
        return flow {
            val updatedCryptos = fetchAndUpdateCryptoData(initialCryptos, params)
            emit(updatedCryptos)
        }
    }

    override suspend fun getBalance(
        symbol: String, userAddress: String
    ): Double {
        return manager.getBalance(symbol, userAddress)
    }

    override suspend fun getFee(params: FeeEstimationParams): Double {
        val estimator = estimators[params.symbol]
        return estimator?.invoke(params.address) ?: 0.0
    }

    override suspend fun getTransactions(params: TransactionParams): List<Transaction> {
        val response = transactionApi.getTransactions(coin = params.coin, address = params.address)
        val transactions = response.txs
        return transactions
    }

    private suspend fun fetchAndUpdateCryptoData(
        cryptos: List<CoinInfo>,
        params: AddressParams,
    ): List<CoinInfo> {
        val cryptoIds = cryptos.joinToString(",") { it.id }
        val prices = cryptoApi.getPrice(cryptoIds)

        val deferredUpdates = coroutineScope {
            cryptos.map { crypto ->
                async {
                    val generatorResult = crypto.generator?.invoke(params)
                    val cachedCoin = cache.getCoin(crypto.symbol)
                    val now = System.currentTimeMillis()

                    val address = if (generatorResult is Resource.Success<*>) {
                        generatorResult.data.toString()
                    } else {
                        ""
                    }

                    val coinToUpdate =
                        if (cachedCoin != null && now - cachedCoin.updatedAt < cacheTTl) {
                            cachedCoin
                        } else {
                            crypto
                        }

                    val updatedCoin = updateCryptoInfo(coinToUpdate, prices, address)

                    return@async updatedCoin
                }
            }
        }

        val finalUpdatedList = deferredUpdates.awaitAll()

        finalUpdatedList.forEach { cache.saveCoin(it) }

        return finalUpdatedList
    }

    private suspend fun updateCryptoInfo(
        crypto: CoinInfo, prices: Map<String, Map<String, Double>>, userAddress: String
    ): CoinInfo {
        val priceData = prices[crypto.id] ?: emptyMap()
        val balance = getBalance(crypto.symbol, userAddress)

        return crypto.copy(
            priceUsd = priceData["usd"] ?: 0.0,
            changePrecent = priceData["usd_24h_change"] ?: 0.0,
            balance = balance
        )
    }
}