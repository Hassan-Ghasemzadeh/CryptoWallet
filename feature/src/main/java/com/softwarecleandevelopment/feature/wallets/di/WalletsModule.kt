package com.softwarecleandevelopment.feature.wallets.di

import com.softwarecleandevelopment.core.database.room.dao.WalletDao
import com.softwarecleandevelopment.feature.wallets.data.datasource.WalletsDataSource
import com.softwarecleandevelopment.feature.wallets.data.datasource.WalletsDataSourceImpl
import com.softwarecleandevelopment.feature.wallets.data.repository.WalletsRepositoryImpl
import com.softwarecleandevelopment.feature.wallets.domain.repository.WalletsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WalletsModule {
    @Provides
    @Singleton
    fun provideWalletsDatasource(dao: WalletDao): WalletsDataSource {
        return WalletsDataSourceImpl(dao)
    }

    @Provides
    @Singleton
    fun provideWalletsRepository(walletsDataSourceImpl: WalletsDataSourceImpl): WalletsRepository {
        return WalletsRepositoryImpl(walletsDataSourceImpl)
    }
}