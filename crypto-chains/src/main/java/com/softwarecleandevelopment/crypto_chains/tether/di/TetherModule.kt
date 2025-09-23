package com.softwarecleandevelopment.crypto_chains.tether.di

import com.softwarecleandevelopment.crypto_chains.tether.data.datasource.TetherDataSource
import com.softwarecleandevelopment.crypto_chains.tether.data.datasource.TetherDataSourceImpl
import com.softwarecleandevelopment.crypto_chains.tether.data.repository.TetherRepositoryImpl
import com.softwarecleandevelopment.crypto_chains.tether.domain.repository.TetherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TetherModule {
    @Provides
    @Singleton
    fun provideTetherDataSource(): TetherDataSource {
        return TetherDataSourceImpl()
    }

    @Provides
    @Singleton
    fun provideTetherRepository(dataSource: TetherDataSource): TetherRepository {
        return TetherRepositoryImpl(dataSource)
    }
}