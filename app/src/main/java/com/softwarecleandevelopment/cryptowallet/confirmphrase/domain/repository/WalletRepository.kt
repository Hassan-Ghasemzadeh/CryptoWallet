package com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.repository

import com.softwarecleandevelopment.core.crypto.ChainType
import com.softwarecleandevelopment.cryptowallet.confirmphrase.data.models.Derived
import com.softwarecleandevelopment.cryptowallet.confirmphrase.data.models.Result
import com.softwarecleandevelopment.cryptowallet.confirmphrase.data.models.WalletEntity
import kotlinx.coroutines.flow.Flow

interface WalletRepository {
    fun wallets(): Flow<List<WalletEntity>>

    suspend fun createNewWallet(
        derived: Derived,
    ): Result<Unit>

    suspend fun importFromMnemonic(
        name: String, chain: ChainType, mnemonic: String
    )

    suspend fun importFromPrivateKey(
        name: String, chain: ChainType, privateKey: String
    )

    suspend fun addWatchOnly(name: String, chain: ChainType, address: String): WalletEntity
}