package com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.repository

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.crypto.models.ChainType
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.models.Derived
import com.softwarecleandevelopment.core.database.room.models.WalletEntity
import kotlinx.coroutines.flow.Flow

interface WalletRepository {

    suspend fun createNewWallet(
        derived: Derived,
    ): Resource<Long>

    suspend fun importFromMnemonic(
        name: String, chain: ChainType, mnemonic: String
    )

    suspend fun importFromPrivateKey(
        name: String, chain: ChainType, privateKey: String
    )

    suspend fun addWatchOnly(name: String, chain: ChainType, address: String): WalletEntity
}