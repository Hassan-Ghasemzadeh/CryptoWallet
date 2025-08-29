package com.softwarecleandevelopment.feature.wallet_home.di

import com.softwarecleandevelopment.feature.wallet_home.domain.usecases.GetActiveWalletUseCase
import com.softwarecleandevelopment.feature.wallets.domain.repository.WalletsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WalletHomeUseCaseModule {

    @Provides
    @Singleton
    fun provideGetActiveWalletUseCase(walletsRepository: WalletsRepository): GetActiveWalletUseCase {
        return GetActiveWalletUseCase(walletsRepository)
    }
}