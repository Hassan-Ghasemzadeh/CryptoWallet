package com.softwarecleandevelopment.cryptowallet.di

import com.softwarecleandevelopment.cryptowallet.data.WalletRepositoryImpl
import com.softwarecleandevelopment.cryptowallet.domain.WalletRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WalletModule {
    @Provides
    @Singleton
    fun provideUserRepository(): WalletRepository {
        return WalletRepositoryImpl()
    }
}