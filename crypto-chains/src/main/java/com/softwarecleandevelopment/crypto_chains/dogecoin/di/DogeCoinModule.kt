package com.softwarecleandevelopment.crypto_chains.dogecoin.di

import com.softwarecleandevelopment.crypto_chains.dogecoin.data.datasource.DogeCoinDataSource
import com.softwarecleandevelopment.crypto_chains.dogecoin.data.datasource.DogeCoinDataSourceImpl
import com.softwarecleandevelopment.crypto_chains.dogecoin.data.repository.DogeCoinRepositoryImpl
import com.softwarecleandevelopment.crypto_chains.dogecoin.domian.repository.DogeCoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DogeCoinModule {
    @Provides
    @Singleton
    fun provideDogeCoinRepository(dataSource: DogeCoinDataSource): DogeCoinRepository {
        return DogeCoinRepositoryImpl(dataSource)
    }

    @Provides
    @Singleton
    fun provideDogeCoinDataSource(): DogeCoinDataSource {
        return DogeCoinDataSourceImpl()
    }

}