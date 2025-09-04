package com.softwarecleandevelopment.feature.wallet_home.di

import android.content.Context
import com.softwarecleandevelopment.feature.wallet_home.domain.usecases.CopyToClipboardUseCase
import com.softwarecleandevelopment.feature.wallet_home.domain.usecases.GenerateQrBitmapUseCase
import com.softwarecleandevelopment.feature.wallet_home.domain.usecases.GetActiveWalletUseCase
import com.softwarecleandevelopment.feature.wallet_home.domain.usecases.ShareTextUseCase
import com.softwarecleandevelopment.feature.wallets.domain.repository.WalletsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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


    @Provides
    @Singleton
    fun provideGenerateQrCodeUseCase(): GenerateQrBitmapUseCase {
        return GenerateQrBitmapUseCase()
    }


    @Provides
    @Singleton
    fun provideCopyToClipBoardUseCase(@ApplicationContext context: Context): CopyToClipboardUseCase {
        return CopyToClipboardUseCase(context)
    }


    @Provides
    @Singleton
    fun provideShareUseCase(@ApplicationContext context: Context): ShareTextUseCase {
        return ShareTextUseCase(context)
    }
}