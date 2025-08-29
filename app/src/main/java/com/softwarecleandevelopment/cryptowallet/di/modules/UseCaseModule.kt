package com.softwarecleandevelopment.cryptowallet.di.modules

import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.repository.WalletRepository
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.usecases.CreateWalletUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideCreateWalletUseCase(walletRepository: WalletRepository): CreateWalletUseCase {
        return CreateWalletUseCase(walletRepository)
    }
}