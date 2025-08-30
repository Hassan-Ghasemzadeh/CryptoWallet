package com.softwarecleandevelopment.crypto_chains.ethereum.di

import com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.EthCryptoApi
import com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.EthCryptoDatasource
import com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.EthCryptoDatasourceImpl
import com.softwarecleandevelopment.crypto_chains.ethereum.data.repository.EthCryptoRepositoryImpl
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.repository.EthCryptoRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@Singleton
object EthCryptoModule {
    @Provides
    @Singleton
    fun provideEthCryptoApi(retrofit: Retrofit): EthCryptoApi {
        return retrofit.create(EthCryptoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEthCryptoDatasource(ethCryptoApi: EthCryptoApi): EthCryptoDatasource {
        return EthCryptoDatasourceImpl(ethCryptoApi)
    }

    @Provides
    @Singleton
    fun provideEthCryptoRepository(ethCryptoDatasource: EthCryptoDatasource): EthCryptoRepository {
        return EthCryptoRepositoryImpl(ethCryptoDatasource)
    }

}