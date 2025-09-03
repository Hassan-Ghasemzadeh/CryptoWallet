package com.softwarecleandevelopment.crypto_chains.ethereum.di

import com.softwarecleandevelopment.core.database.room.dao.WalletDao
import com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.local.EthLocalDatasource
import com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.local.EthLocalDatasourceImpl
import com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.remote.EthCryptoApi
import com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.remote.EthCryptoRemoteDatasource
import com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.remote.EthCryptoRemoteDatasourceImpl
import com.softwarecleandevelopment.crypto_chains.ethereum.data.repository.local.EthLocalRepositoryImpl
import com.softwarecleandevelopment.crypto_chains.ethereum.data.repository.remote.EthCryptoRemoteRepositoryImpl
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.repository.local.EthLocalRepository
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.repository.remote.EthCryptoRemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EthCryptoModule {
    @Provides
    @Singleton
    fun provideEthCryptoApi(retrofit: Retrofit): EthCryptoApi {
        return retrofit.create(EthCryptoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEthCryptoDatasource(ethCryptoApi: EthCryptoApi): EthCryptoRemoteDatasource {
        return EthCryptoRemoteDatasourceImpl(ethCryptoApi)
    }

    @Provides
    @Singleton
    fun provideEthCryptoRepository(ethCryptoDatasource: EthCryptoRemoteDatasource): EthCryptoRemoteRepository {
        return EthCryptoRemoteRepositoryImpl(ethCryptoDatasource)
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