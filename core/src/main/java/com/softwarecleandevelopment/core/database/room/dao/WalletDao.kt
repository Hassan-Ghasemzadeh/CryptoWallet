package com.softwarecleandevelopment.core.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.softwarecleandevelopment.core.database.room.models.WalletEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WalletDao {
    @Insert(onConflict = OnConflictStrategy.Companion.ABORT)
    suspend fun insert(wallet: WalletEntity)

    @Query("SELECT * FROM wallets ORDER BY createdAt ASC")
    fun getAllWallets(): Flow<List<WalletEntity>>

    @Query("SELECT * FROM wallets WHERE isActive = 1 LIMIT 1")
    fun getActiveWallet(): Flow<WalletEntity?>

    @Query("UPDATE wallets SET isActive = 0")
    suspend fun deactivateAll()

    @Query("UPDATE wallets SET isActive = 1 WHERE id = :walletId")
    suspend fun activateWallet(walletId: Int)
}