package com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.remote

import android.util.Log
import com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.remote.EthCryptoApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.http.HttpService
import java.math.BigDecimal
import javax.inject.Inject

class EthCryptoRemoteDatasourceImpl @Inject constructor(val ethApi: EthCryptoApi) : EthCryptoRemoteDatasource {
    private val ethRpcUrl = "https://mainnet.infura.io/v3/ce064e40a69b4971a4e28afcb113baa0"
    override suspend fun getPrice(
        ids: String, change: Boolean
    ): Map<String, Map<String, Double>> {
        return ethApi.getPrice(ids, change = change)
    }

    override suspend fun getBalance(
        symbol: String, userAddress: String
    ): Double {
        return when (symbol) {
            "ETH" -> getEthBalance(ethRpcUrl, userAddress)
            else -> {
                TODO("Not yet implemented")
            }
        }
    }

    override suspend fun getEthBalance(
        rpcUrl: String, address: String
    ): Double {
        return withContext(Dispatchers.IO) {
            try {
                val web3 = Web3j.build(HttpService(rpcUrl))
                val balanceWei =
                    web3.ethGetBalance(address, DefaultBlockParameterName.LATEST).send().balance
                balanceWei.toBigDecimal().divide(BigDecimal.TEN.pow(18)).toDouble()
            } catch (e: Exception) {
                Log.d("EthCryptoDatasourceImpl", "getEthBalance: ${e.message}")
                0.0
            }
        }
    }

}