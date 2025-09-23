package com.softwarecleandevelopment.crypto_chains.ethereum.di

import com.softwarecleandevelopment.core.database.room.dao.WalletDao
import com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.EthDatasource
import com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.EthRemoteDatasourceImpl
import com.softwarecleandevelopment.crypto_chains.ethereum.data.repository.EthRepositoryImpl
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.repository.EthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EthCryptoModule {

    @Provides
    @Singleton
    fun provideEthCryptoDatasource(dao: WalletDao): EthDatasource {
        return EthRemoteDatasourceImpl(dao)
    }

    @Provides
    @Singleton
    fun provideEthCryptoRepository(ethCryptoDatasource: EthDatasource): EthRepository {
        return EthRepositoryImpl(ethCryptoDatasource)
    }
}