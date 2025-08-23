package com.softwarecleandevelopment.cryptowallet.di.modules

import android.content.Context
import androidx.room.Room
import com.softwarecleandevelopment.core.crypto.CryptoStore
import com.softwarecleandevelopment.core.database.WalletSecureStorage
import com.softwarecleandevelopment.cryptowallet.confirmphrase.data.database.WalletDatabase
import com.softwarecleandevelopment.cryptowallet.confirmphrase.data.source.WalletRepositoryImpl
import com.softwarecleandevelopment.cryptowallet.confirmphrase.data.models.WalletDao
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.repository.WalletRepository
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.data.PhraseRepositoryImpl
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.domain.PhraseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WalletModule {

    @Provides
    @Singleton
    fun provideUserRepository(storage: WalletSecureStorage): PhraseRepository {
        return PhraseRepositoryImpl(storage)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): WalletDatabase {
        return Room.databaseBuilder(
            context,
            WalletDatabase::class.java,
            "wallet_database",
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(walletDatabase: WalletDatabase): WalletDao {
        return walletDatabase.walletDao()
    }

    @Provides
    @Singleton
    fun provideWalletRepository(dao: WalletDao, cryptoStore: CryptoStore): WalletRepository {
        return WalletRepositoryImpl(dao, cryptoStore = cryptoStore)

    }
}