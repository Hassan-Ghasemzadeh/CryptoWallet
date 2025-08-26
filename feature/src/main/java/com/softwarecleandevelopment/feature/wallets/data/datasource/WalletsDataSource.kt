package com.softwarecleandevelopment.feature.wallets.data.datasource

import com.softwarecleandevelopment.core.database.room.models.WalletEntity
import kotlinx.coroutines.flow.Flow

interface WalletsDataSource {
    suspend fun selectWallets(walletId: Long)

    fun getWallets(): Flow<List<WalletEntity>>

    fun getActiveWallet(): Flow<WalletEntity?>
}