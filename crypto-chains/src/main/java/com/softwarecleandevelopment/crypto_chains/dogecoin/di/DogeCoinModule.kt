package com.softwarecleandevelopment.crypto_chains.dogecoin.di

import com.softwarecleandevelopment.core.di.modules.BlockCypherRetrofit
import com.softwarecleandevelopment.crypto_chains.dogecoin.data.datasource.DogeCoinDataSource
import com.softwarecleandevelopment.crypto_chains.dogecoin.data.datasource.DogeCoinDataSourceImpl
import com.softwarecleandevelopment.crypto_chains.dogecoin.data.datasource.DogecoinApi
import com.softwarecleandevelopment.crypto_chains.dogecoin.data.datasource.DogecoinFeeEstimator
import com.softwarecleandevelopment.crypto_chains.dogecoin.data.repository.DogeCoinRepositoryImpl
import com.softwarecleandevelopment.crypto_chains.dogecoin.domain.repository.DogeCoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DogeCoinModule {
    @Provides
    @Singleton
    fun provideBitcoinApi(@BlockCypherRetrofit retrofit: Retrofit): DogecoinApi {
        return retrofit.create(DogecoinApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDogecoinFeeEstimator(): DogecoinFeeEstimator {
        return DogecoinFeeEstimator()
    }

    @Provides
    @Singleton
    fun provideDogeCoinDataSource(
        api: DogecoinApi,
        estimator: DogecoinFeeEstimator
    ): DogeCoinDataSource {
        return DogeCoinDataSourceImpl(api, estimator)
    }

    @Provides
    @Singleton
    fun provideDogeCoinRepository(dataSource: DogeCoinDataSource): DogeCoinRepository {
        return DogeCoinRepositoryImpl(dataSource)
    }

}