package com.softwarecleandevelopment.crypto_chains.ethereum.di

import com.softwarecleandevelopment.core.database.room.dao.WalletDao
import com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.local.EthLocalDatasource
import com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.local.EthLocalDatasourceImpl
import com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.remote.EthRemoteDatasource
import com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.remote.EthRemoteDatasourceImpl
import com.softwarecleandevelopment.crypto_chains.ethereum.data.repository.local.EthLocalRepositoryImpl
import com.softwarecleandevelopment.crypto_chains.ethereum.data.repository.remote.EthRemoteRepositoryImpl
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.repository.local.EthLocalRepository
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.repository.remote.EthRemoteRepository
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
    fun provideEthCryptoDatasource(): EthRemoteDatasource {
        return EthRemoteDatasourceImpl()
    }

    @Provides
    @Singleton
    fun provideEthCryptoRepository(ethCryptoDatasource: EthRemoteDatasource): EthRemoteRepository {
        return EthRemoteRepositoryImpl(ethCryptoDatasource)
    }

    @Provides
    @Singleton
    fun provideEthLocalDatasource(dao: WalletDao): EthLocalDatasource {
        return EthLocalDatasourceImpl(dao)
    }

    @Provides
    @Singleton
    fun provideEthLocalRepository(ethCryptoDatasource: EthLocalDatasource): EthLocalRepository {
        return EthLocalRepositoryImpl(ethCryptoDatasource)
    }
}