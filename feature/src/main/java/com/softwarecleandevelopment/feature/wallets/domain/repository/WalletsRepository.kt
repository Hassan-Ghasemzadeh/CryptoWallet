package com.softwarecleandevelopment.feature.wallets.domain.repository

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.database.room.models.WalletEntity
import kotlinx.coroutines.flow.Flow

interface WalletsRepository {
    fun getWallets(): Resource<Flow<List<WalletEntity>>>
    suspend fun selectWallets(walletId: Long): Resource<Unit>
    fun getActiveWallet(): Resource<Flow<WalletEntity?>>
}