package com.softwarecleandevelopment.crypto_chains.crypto_info.di

import com.softwarecleandevelopment.crypto_chains.crypto_info.data.datasource.CryptoApi
import com.softwarecleandevelopment.crypto_chains.crypto_info.data.datasource.CryptoInfoDataSourceImpl
import com.softwarecleandevelopment.crypto_chains.crypto_info.data.datasource.CryptoInfoDatasource
import com.softwarecleandevelopment.crypto_chains.crypto_info.data.repository.CryptoInfoRepositoryImpl
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.repository.CryptoInfoRepository
import com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.EthDatasource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CryptoInfoModule {
    @Provides
    @Singleton
    fun provideEthCryptoApi(retrofit: Retrofit): CryptoApi {
        return retrofit.create(CryptoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCryptoInfoDataSource(
        api: CryptoApi,
        ethRemoteDatasource: EthDatasource
    ): CryptoInfoDatasource {
        return CryptoInfoDataSourceImpl(api, ethRemoteDatasource)
    }

    @Provides
    @Singleton
    fun provideCryptoInfoRepository(datasource: CryptoInfoDatasource): CryptoInfoRepository {
        return CryptoInfoRepositoryImpl(datasource = datasource)
    }
}