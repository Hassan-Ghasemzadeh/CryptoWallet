package com.softwarecleandevelopment.crypto_chains.ethereum.data.repository.remote

import android.os.Build
import androidx.annotation.RequiresApi
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.safeCall
import com.softwarecleandevelopment.core.common.utils.safeFlowCall
import com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.remote.EthRemoteDatasource
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.CryptoInfo
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.SendResult
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.SendTokenEvent
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.repository.remote.EthRemoteRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.math.BigInteger
import javax.inject.Inject

class EthRemoteRepositoryImpl @Inject constructor(private val datasource: EthRemoteDatasource) :
    EthRemoteRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getCryptoInfo(
        cryptos: List<CryptoInfo>, userAddress: String
    ): Resource<Flow<List<CryptoInfo>>> {
        val ids = cryptos.joinToString(",") { it.id }


        return safeFlowCall {
            flow {
                val prices = datasource.getPrice(ids)
                coroutineScope {
                    val updatedCryptos = cryptos.map {
                        async {
                            val data: Map<String, Double> =
                                prices[it.id] ?: emptyMap<String, Double>()
                            it.copy(
                                priceUsd = data["usd"] ?: 0.0,
                                changePrecent = data["usd_24h_change"] ?: 0.0,
                                balance = datasource.getBalance(it.symbol, userAddress)
                            )
                        }
                    }.awaitAll()
                    emit(updatedCryptos)
                }
            }
        }
    }

    override suspend fun send(params: SendTokenEvent): Resource<SendResult> {
        return safeCall { datasource.send(params) }
    }

    override suspend fun estimateNetworkFee(
        rpcUrl: String, tokenContractAddress: String?
    ): Resource<Pair<BigInteger, BigInteger>> {
        return safeCall { datasource.estimateNetworkFee(tokenContractAddress) }
    }
}