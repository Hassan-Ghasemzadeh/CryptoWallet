package com.softwarecleandevelopment.crypto_chains.crypto_info.di

import com.softwarecleandevelopment.crypto_chains.R
import com.softwarecleandevelopment.crypto_chains.bitcoin.domain.usecase.GenerateBitcoinAddressUseCase
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.model.CryptoInfo
import com.softwarecleandevelopment.crypto_chains.dogecoin.domain.usecase.GenerateDogeCoinAddressUseCase
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.usecases.GenerateEthAddressUseCase
import com.softwarecleandevelopment.crypto_chains.tether.domain.usecase.GenerateTetherAddressUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CryptoList {

    @Provides
    @Singleton
    fun provideInitialCryptos(
        ethAddressUseCase: GenerateEthAddressUseCase,
        bitcoinAddressUseCase: GenerateBitcoinAddressUseCase,
        dogeAddressUseCase: GenerateDogeCoinAddressUseCase,
        tetherAddressUseCase: GenerateTetherAddressUseCase
    ): List<CryptoInfo> {

        val initialCryptos = listOf(
            CryptoInfo(
                id = "ethereum",
                symbol = "ETH",
                name = "Ethereum",
                iconRes = R.drawable.ic_eth,
                generator = ethAddressUseCase
            ),
            CryptoInfo(
                id = "bitcoin",
                symbol = "BTC",
                name = "Bitcoin",
                iconRes = R.drawable.ic_btc,
                generator = bitcoinAddressUseCase
            ),
            CryptoInfo(
                id = "doge",
                symbol = "DOGE",
                name = "Dogecoin",
                iconRes = R.drawable.ic_doge,
                generator = dogeAddressUseCase
            ),
            CryptoInfo(
                id = "tether",
                symbol = "USDT",
                name = "Tether",
                iconRes = R.drawable.ic_usdt,
                generator = tetherAddressUseCase
            )
        )
        return initialCryptos
    }
}