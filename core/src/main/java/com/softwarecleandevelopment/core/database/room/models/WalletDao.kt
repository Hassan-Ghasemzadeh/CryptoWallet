package com.softwarecleandevelopment.core.database.room.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WalletDao {
    @Insert(onConflict = OnConflictStrategy.Companion.ABORT)
    suspend fun insert(wallet: WalletEntity)

    @Query("SELECT * FROM wallets ORDER BY createdAt DESC")
    fun observeAll(): Flow<List<WalletEntity>>

}