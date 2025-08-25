package com.softwarecleandevelopment.cryptowallet.di.modules

import com.softwarecleandevelopment.core.crypto.security.CryptoStore
import com.softwarecleandevelopment.core.database.seed_datastore.SecureSeedStorage
import com.softwarecleandevelopment.cryptowallet.confirmphrase.data.repository.WalletRepositoryImpl
import com.softwarecleandevelopment.core.database.room.dao.WalletDao
import com.softwarecleandevelopment.cryptowallet.confirmphrase.data.datasource.WalletDataSource
import com.softwarecleandevelopment.cryptowallet.confirmphrase.data.datasource.WalletDataSourceImpl
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.repository.WalletRepository
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.data.datasource.PhraseDataSourceImpl
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.data.datasource.PhraseDatasource
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.data.repository.PhraseRepositoryImpl
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
    fun providePhraseDataSource(storage: SecureSeedStorage): PhraseDatasource {
        return PhraseDataSourceImpl(storage)
    }

    @Provides
    @Singleton
    fun providePhraseRepository(dataSourceImpl: PhraseDataSourceImpl): PhraseRepository {
        return PhraseRepositoryImpl(dataSourceImpl)
    }

    fun provideWalletDataSource(dao: WalletDao, cryptoStore: CryptoStore): WalletDataSource {
        return WalletDataSourceImpl(dao, cryptoStore)
    }

    @Provides
    @Singleton
    fun provideWalletRepository(walletDataSourceImpl: WalletDataSourceImpl): WalletRepository {
        return WalletRepositoryImpl(walletDataSourceImpl)

    }
}