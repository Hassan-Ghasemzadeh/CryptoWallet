package com.softwarecleandevelopment.cryptowallet.confirmphrase.data.repository

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.safeCall
import com.softwarecleandevelopment.core.crypto.models.ChainType
import com.softwarecleandevelopment.core.database.room.models.WalletEntity
import com.softwarecleandevelopment.cryptowallet.confirmphrase.data.datasource.WalletDataSourceImpl
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.models.Derived
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.repository.WalletRepository
import javax.inject.Inject

class WalletRepositoryImpl @Inject constructor(
    private val walletDataSourceImpl: WalletDataSourceImpl,
) : WalletRepository {

    override suspend fun createNewWallet(derived: Derived): Resource<Unit> {
        return safeCall { walletDataSourceImpl.createNewWallet(derived) }
    }

    override suspend fun importFromMnemonic(
        name: String, chain: ChainType, mnemonic: String
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun importFromPrivateKey(
        name: String, chain: ChainType, privateKey: String
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun addWatchOnly(
        name: String, chain: ChainType, address: String
    ): WalletEntity {
        TODO("Not yet implemented")
    }

}