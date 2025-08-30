package com.softwarecleandevelopment.crypto_chains.ethereum.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.safeFlowCall
import com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.EthCryptoDatasource
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.CryptoInfo
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.repository.EthCryptoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.collections.emptyMap

class EthCryptoRepositoryImpl @Inject constructor(private val ethCryptoDatasource: EthCryptoDatasource) :
    EthCryptoRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getCryptoInfo(
        cryptos: List<CryptoInfo>,
        userAddress: String
    ): Resource<Flow<List<CryptoInfo>>> {
        val ids = cryptos.joinToString(",") { it.id }
        val prices = ethCryptoDatasource.getPrice(ids)
        return safeFlowCall {
            flow {
                cryptos.map {
                    val data: Map<String, Double> = prices[it.id] ?: emptyMap<String, Double>()
                    it.copy(
                        priceUsd = data["usd"] ?: 0.0,
                        changePrecent = data["usd_24h_change"] ?: 0.0,
                        balance = ethCryptoDatasource.getBalance(it.symbol, userAddress)
                    )
                }
            }
        }
    }
}