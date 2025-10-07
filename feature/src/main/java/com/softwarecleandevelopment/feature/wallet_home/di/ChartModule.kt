package com.softwarecleandevelopment.feature.wallet_home.di

import com.softwarecleandevelopment.core.di.modules.CryptoInfoRetrofit
import com.softwarecleandevelopment.feature.wallet_home.data.datasource.ChartCoinApi
import com.softwarecleandevelopment.feature.wallet_home.data.datasource.ChartCoinDataSource
import com.softwarecleandevelopment.feature.wallet_home.data.datasource.ChartCoinDataSourceImpl
import com.softwarecleandevelopment.feature.wallet_home.data.datasource.LivePriceSocketManager
import com.softwarecleandevelopment.feature.wallet_home.data.repository.ChartCoinRepositoryImpl
import com.softwarecleandevelopment.feature.wallet_home.domain.repository.ChartCoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChartModule {
    @Provides
    @Singleton
    fun provideChartApi(@CryptoInfoRetrofit retrofit: Retrofit): ChartCoinApi =
        retrofit.create(ChartCoinApi::class.java)

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().pingInterval(20, TimeUnit.SECONDS).build()

    @Provides
    @Singleton
    fun provideLivePriceSocketManager(okHttpClient: OkHttpClient): LivePriceSocketManager {
        return LivePriceSocketManager(okHttpClient)
    }

    @Provides
    @Singleton
    fun provideChartCoinDataSource(
        api: ChartCoinApi,
        socketManager: LivePriceSocketManager,
    ): ChartCoinDataSource {
        return ChartCoinDataSourceImpl(api, socketManager)
    }

    @Provides
    @Singleton
    fun provideChartCoinRepository(dataSource: ChartCoinDataSource): ChartCoinRepository {
        return ChartCoinRepositoryImpl(dataSource)
    }
}