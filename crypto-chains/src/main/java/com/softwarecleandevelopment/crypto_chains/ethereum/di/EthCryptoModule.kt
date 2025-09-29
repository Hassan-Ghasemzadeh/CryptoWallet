package com.softwarecleandevelopment.crypto_chains.ethereum.di

import com.softwarecleandevelopment.core.common.utils.FeeEstimator
import com.softwarecleandevelopment.core.database.room.dao.WalletDao
import com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.EthDatasource
import com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.EthFeeEstimator
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
    fun provideFeeEstimator(): FeeEstimator {
        return EthFeeEstimator()
    }

    @Provides
    @Singleton
    fun provideEthCryptoDatasource(dao: WalletDao, estimator: EthFeeEstimator): EthDatasource {
        return EthRemoteDatasourceImpl(dao, estimator)
    }

    @Provides
    @Singleton
    fun provideEthCryptoRepository(ethCryptoDatasource: EthDatasource): EthRepository {
        return EthRepositoryImpl(ethCryptoDatasource)
    }
}