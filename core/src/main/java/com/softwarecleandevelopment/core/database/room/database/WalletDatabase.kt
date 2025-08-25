package com.softwarecleandevelopment.core.database.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.softwarecleandevelopment.core.database.room.dao.WalletDao
import com.softwarecleandevelopment.core.database.room.models.WalletEntity

@Database(entities = [WalletEntity::class], version = 1, exportSchema = false)
abstract class WalletDatabase : RoomDatabase() {
    abstract fun walletDao(): WalletDao
}