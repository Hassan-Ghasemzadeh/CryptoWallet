package com.softwarecleandevelopment.cryptowallet.di.modules

import com.softwarecleandevelopment.core.crypto.security.CryptoStore
import com.softwarecleandevelopment.core.database.seed_datastore.SecureSeedStorage
import com.softwarecleandevelopment.cryptowallet.confirmphrase.data.source.WalletRepositoryImpl
import com.softwarecleandevelopment.core.database.room.dao.WalletDao
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.repository.WalletRepository
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.data.PhraseRepositoryImpl
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.domain.repository.PhraseRepository
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
    fun provideUserRepository(storage: SecureSeedStorage): PhraseRepository {
        return PhraseRepositoryImpl(storage)
    }
    @Provides
    @Singleton
    fun provideWalletRepository(dao: WalletDao, cryptoStore: CryptoStore): WalletRepository {
        return WalletRepositoryImpl(dao, cryptoStore = cryptoStore)

    }
}