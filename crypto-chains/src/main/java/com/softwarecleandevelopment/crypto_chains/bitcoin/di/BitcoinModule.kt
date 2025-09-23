package com.softwarecleandevelopment.crypto_chains.bitcoin.di

import com.softwarecleandevelopment.crypto_chains.bitcoin.data.datasource.BitcoinDataSource
import com.softwarecleandevelopment.crypto_chains.bitcoin.data.datasource.BitcoinDataSourceImpl
import com.softwarecleandevelopment.crypto_chains.bitcoin.data.repository.BitcoinRepositoryImpl
import com.softwarecleandevelopment.crypto_chains.bitcoin.domain.repository.BitcoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BitcoinModule {
    @Provides
    @Singleton
    fun provideBitcoinDataSource(): BitcoinDataSource {
        return BitcoinDataSourceImpl()
    }

    @Provides
    @Singleton
    fun provideBitcoinRepository(dataSource: BitcoinDataSource): BitcoinRepository {
        return BitcoinRepositoryImpl(dataSource)
    }
}