package com.softwarecleandevelopment.crypto_chains.crypto_info.data.utils

import com.softwarecleandevelopment.core.common.utils.BalanceUseCase
import com.softwarecleandevelopment.crypto_chains.bitcoin.domain.usecase.GetBitcoinBalanceUseCase
import com.softwarecleandevelopment.crypto_chains.dogecoin.domain.usecase.GetDogeCoinBalanceUseCase
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.usecases.GetEthBalanceUseCase
import com.softwarecleandevelopment.crypto_chains.tether.domain.usecase.GetTetherBalanceUseCase
import javax.inject.Inject

class BalanceManager @Inject constructor(
    ethBalanceUseCase: GetEthBalanceUseCase,
    tetherBalanceUseCase: GetTetherBalanceUseCase,
    dogeCoinBalanceUseCase: GetDogeCoinBalanceUseCase,
    bitcoinBalanceUseCase: GetBitcoinBalanceUseCase,
) {
    private val balanceFetchers: Map<String, BalanceUseCase> = mapOf(
        "ETH" to ethBalanceUseCase,
        "USDT" to tetherBalanceUseCase,
        "DOGE" to dogeCoinBalanceUseCase,
        "BTC" to bitcoinBalanceUseCase,
    )

    suspend fun getBalance(
        symbol: String, userAddress: String
    ): Double {
        val fetcher =
            balanceFetchers[symbol] ?: throw IllegalArgumentException("Unsupported symbol: $symbol")

        return fetcher.invoke(userAddress)
    }
}