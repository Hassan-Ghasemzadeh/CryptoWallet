package com.softwarecleandevelopment.cryptowallet.confirmphrase.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.softwarecleandevelopment.cryptowallet.confirmphrase.data.models.WalletDao
import com.softwarecleandevelopment.cryptowallet.confirmphrase.data.models.WalletEntity

@Database(entities = [WalletEntity::class], version = 1, exportSchema = false)
abstract class WalletDatabase : RoomDatabase() {
    abstract fun walletDao(): WalletDao
}