package com.softwarecleandevelopment.crypto_chains.ethereum.data.repository.remote

import android.os.Build
import androidx.annotation.RequiresApi
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.safeFlowCall
import com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.remote.EthCryptoRemoteDatasource
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.CryptoInfo
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.repository.remote.EthCryptoRemoteRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EthCryptoRemoteRepositoryImpl @Inject constructor(private val ethCryptoDatasource: EthCryptoRemoteDatasource) :
    EthCryptoRemoteRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getCryptoInfo(
        cryptos: List<CryptoInfo>,
        userAddress: String
    ): Resource<Flow<List<CryptoInfo>>> {
        val ids = cryptos.joinToString(",") { it.id }


        return safeFlowCall {
            flow {
                val prices = ethCryptoDatasource.getPrice(ids)
                coroutineScope {
                    val updatedCryptos = cryptos.map {
                        async {
                            val data: Map<String, Double> = prices[it.id] ?: emptyMap<String, Double>()
                            it.copy(
                                priceUsd = data["usd"] ?: 0.0,
                                changePrecent = data["usd_24h_change"] ?: 0.0,
                                balance = ethCryptoDatasource.getBalance(it.symbol, userAddress)
                            )
                        }
                    }.awaitAll()
                    emit(updatedCryptos)
                }
            }
        }
    }
}