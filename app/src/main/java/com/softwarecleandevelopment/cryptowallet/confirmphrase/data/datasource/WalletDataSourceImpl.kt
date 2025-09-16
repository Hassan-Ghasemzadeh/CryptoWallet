package com.softwarecleandevelopment.cryptowallet.confirmphrase.data.datasource

import android.util.Base64
import com.softwarecleandevelopment.core.crypto.models.ChainType
import com.softwarecleandevelopment.core.crypto.security.CryptoStore
import com.softwarecleandevelopment.core.database.room.dao.WalletDao
import com.softwarecleandevelopment.core.database.room.models.WalletEntity
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.models.Derived
import javax.inject.Inject

class WalletDataSourceImpl @Inject constructor(
    private val walletDao: WalletDao, private val cryptoStore: CryptoStore
) : WalletDataSource {
    override suspend fun createNewWallet(derived: Derived): Long {
        val encryptedMnemonic = cryptoStore.encrypt(derived.mnemonic.toByteArray(Charsets.UTF_8))
        val encryptedPrivateKey =
            cryptoStore.encrypt(derived.privateKeyHex!!.toByteArray(Charsets.UTF_8))
        val mnemonic = Base64.encodeToString(encryptedMnemonic, Base64.DEFAULT)
        val privateKey = Base64.encodeToString(encryptedPrivateKey, Base64.DEFAULT)
        val count = walletDao.getWalletCount()
        val name = "Wallet #${count + 1}"
        val entity = WalletEntity(
            chain = ChainType.MULTI_COIN.displayName,
            address = derived.address,
            publicKeyHex = derived.publicKeyHex,
            privateKey = privateKey,
            mnemonic = mnemonic,
            isActive = true,
            name = name
        )
        walletDao.deactivateAll()
        val id = walletDao.insert(entity)
        return id
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