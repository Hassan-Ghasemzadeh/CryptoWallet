package com.softwarecleandevelopment.feature.wallets.data.datasource

import com.softwarecleandevelopment.core.database.room.dao.WalletDao
import com.softwarecleandevelopment.core.database.room.models.WalletEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WalletsDataSourceImpl @Inject constructor(
    val dao: WalletDao,
) : WalletsDataSource {
    override suspend fun selectWallets(walletId: Long) {
        dao.deactivateAll()
        dao.activateWallet(walletId)
    }

    override fun getWallets(): Flow<List<WalletEntity>> {
        return dao.getAllWallets()
    }

    override fun getActiveWallet(): Flow<WalletEntity?> {
        return dao.getActiveWallet()
    }

    override suspend fun updateWalletName(name: String, walletId: Long) {
        return dao.updateWalletName(name, walletId)
    }

}