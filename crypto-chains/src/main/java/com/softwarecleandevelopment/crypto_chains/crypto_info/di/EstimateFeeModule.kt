package com.softwarecleandevelopment.crypto_chains.crypto_info.di

import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.crypto_chains.bitcoin.domain.usecase.EstimateBtcFeeUseCase
import com.softwarecleandevelopment.crypto_chains.dogecoin.domain.usecase.EstimateDogeFeeUseCase
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.usecases.EstimateNetworkFeeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EstimateFeeModule {


    @Provides
    @IntoMap
    @StringKey("BTC")
    fun provideBtcFee(btcFeeEstimator: EstimateBtcFeeUseCase): UseCase<Double, String> {
        return btcFeeEstimator
    }

    @Provides
    @IntoMap
    @StringKey("DOGE")
    fun provideDogeFee(dogecoinFeeEstimator: EstimateDogeFeeUseCase): UseCase<Double, String> {
        return dogecoinFeeEstimator
    }

    @Provides
    @IntoMap
    @StringKey("ETH")
    fun provideEthFee(ethFeeEstimator: EstimateNetworkFeeUseCase): UseCase<Double, String> {
        return ethFeeEstimator
    }

    @Provides
    @IntoMap
    @StringKey("USDT")
    fun provideUsdtFee(ethFeeEstimator: EstimateNetworkFeeUseCase): UseCase<Double, String> {
        return ethFeeEstimator
    }

}