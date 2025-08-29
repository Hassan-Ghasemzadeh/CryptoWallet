package com.softwarecleandevelopment.feature.wallets.di

import com.softwarecleandevelopment.feature.wallets.domain.repository.WalletsRepository
import com.softwarecleandevelopment.feature.wallets.domain.usecase.DeleteWalletUseCase
import com.softwarecleandevelopment.feature.wallets.domain.usecase.GetWalletsUseCase
import com.softwarecleandevelopment.feature.wallets.domain.usecase.SelectWalletUseCase
import com.softwarecleandevelopment.feature.wallets.domain.usecase.UpdateWalletUseCase
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
    fun provideGetWalletUseCase(walletsRepository: WalletsRepository): GetWalletsUseCase {
        return GetWalletsUseCase(walletsRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteWalletUseCase(walletsRepository: WalletsRepository): DeleteWalletUseCase {
        return DeleteWalletUseCase(walletsRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateWalletUseCase(walletsRepository: WalletsRepository): UpdateWalletUseCase {
        return UpdateWalletUseCase(walletsRepository)
    }

    @Provides
    @Singleton
    fun provideSelectWalletUseCase(walletsRepository: WalletsRepository): SelectWalletUseCase {
        return SelectWalletUseCase(walletsRepository)
    }

}