package com.softwarecleandevelopment.cryptowallet.confirmphrase.data.datasource

import com.softwarecleandevelopment.core.crypto.models.ChainType
import com.softwarecleandevelopment.core.crypto.security.CryptoStore
import com.softwarecleandevelopment.core.database.room.dao.WalletDao
import com.softwarecleandevelopment.core.database.room.models.WalletEntity
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.models.Derived
import javax.inject.Inject

class WalletDataSourceImpl @Inject constructor(
    private val walletDao: WalletDao, private val cryptoStore: CryptoStore
) : WalletDataSource {
    override suspend fun createNewWallet(derived: Derived) {
        val encryptedMnemonic = cryptoStore.encrypt(derived.mnemonic.toByteArray())
        val encryptedPrivateKey = cryptoStore.encrypt(derived.privateKeyHex!!.toByteArray())
        val entity = WalletEntity(
            chain = ChainType.MULTI_COIN.displayName,
            address = derived.address,
            publicKeyHex = derived.publicKeyHex,
            privateKey = encryptedPrivateKey.toString(),
            mnemonic = encryptedMnemonic.toString(),
            isActive = true,
        )
        walletDao.deactivateAll()
        walletDao.insert(entity)
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