package com.softwarecleandevelopment.crypto_chains.bitcoin.di

import com.softwarecleandevelopment.core.di.modules.BlockCypherRetrofit
import com.softwarecleandevelopment.crypto_chains.bitcoin.data.datasource.BitcoinApi
import com.softwarecleandevelopment.crypto_chains.bitcoin.data.datasource.BitcoinDataSource
import com.softwarecleandevelopment.crypto_chains.bitcoin.data.datasource.BitcoinDataSourceImpl
import com.softwarecleandevelopment.crypto_chains.bitcoin.data.repository.BitcoinRepositoryImpl
import com.softwarecleandevelopment.crypto_chains.bitcoin.domain.repository.BitcoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BitcoinModule {

    @Provides
    @Singleton
    fun provideBitcoinApi(@BlockCypherRetrofit retrofit: Retrofit): BitcoinApi {
        return retrofit.create(BitcoinApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBitcoinDataSource(api: BitcoinApi): BitcoinDataSource {
        return BitcoinDataSourceImpl(api)
    }

    @Provides
    @Singleton
    fun provideBitcoinRepository(dataSource: BitcoinDataSource): BitcoinRepository {
        return BitcoinRepositoryImpl(dataSource)
    }
}