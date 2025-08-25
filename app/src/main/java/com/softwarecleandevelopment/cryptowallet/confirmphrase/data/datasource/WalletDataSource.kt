package com.softwarecleandevelopment.cryptowallet.confirmphrase.data.datasource

import com.softwarecleandevelopment.core.crypto.models.ChainType
import com.softwarecleandevelopment.core.database.room.models.WalletEntity
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.models.Derived

interface WalletDataSource {

    suspend fun createNewWallet(
        derived: Derived,
    ): Long

    suspend fun importFromMnemonic(
        name: String, chain: ChainType, mnemonic: String
    )

    suspend fun importFromPrivateKey(
        name: String, chain: ChainType, privateKey: String
    )

    suspend fun addWatchOnly(name: String, chain: ChainType, address: String): WalletEntity
}