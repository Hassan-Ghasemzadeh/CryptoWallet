package com.softwarecleandevelopment.core.di.modules

import android.content.Context
import androidx.room.Room
import com.softwarecleandevelopment.core.database.room.database.WalletDatabase
import com.softwarecleandevelopment.core.database.room.dao.WalletDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

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


}