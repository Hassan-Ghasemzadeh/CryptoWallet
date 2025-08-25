package com.softwarecleandevelopment.core.database.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.softwarecleandevelopment.core.database.room.models.WalletDao
import com.softwarecleandevelopment.core.database.room.models.WalletEntity

@Database(entities = [WalletEntity::class], version = 2, exportSchema = false)
abstract class WalletDatabase : RoomDatabase() {
    abstract fun walletDao(): WalletDao
}