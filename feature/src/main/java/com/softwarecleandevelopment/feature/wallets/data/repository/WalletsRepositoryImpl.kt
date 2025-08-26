package com.softwarecleandevelopment.feature.wallets.data.repository

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.safeCall
import com.softwarecleandevelopment.core.common.utils.safeFlowCall
import com.softwarecleandevelopment.core.database.room.models.WalletEntity
import com.softwarecleandevelopment.feature.wallets.data.datasource.WalletsDataSourceImpl
import com.softwarecleandevelopment.feature.wallets.domain.repository.WalletsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WalletsRepositoryImpl @Inject constructor(
    val walletsDataSourceImpl: WalletsDataSourceImpl
) : WalletsRepository {
    override fun getWallets(): Resource<Flow<List<WalletEntity>>> {
        return safeFlowCall { walletsDataSourceImpl.getWallets() }
    }

    override suspend fun selectWallets(walletId: Long): Resource<Unit> {
        return safeCall { walletsDataSourceImpl.selectWallets(walletId) }
    }

    override fun getActiveWallet(): Resource<Flow<WalletEntity?>> {
        return safeFlowCall { walletsDataSourceImpl.getActiveWallet() }
    }
}