package com.softwarecleandevelopment.cryptowallet.confirmphrase.data.source

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.crypto.models.ChainType
import com.softwarecleandevelopment.core.crypto.security.CryptoStore
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.models.Derived
import com.softwarecleandevelopment.core.common.utils.safeCall
import com.softwarecleandevelopment.core.database.room.models.WalletDao
import com.softwarecleandevelopment.core.database.room.models.WalletEntity
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.repository.WalletRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WalletRepositoryImpl @Inject constructor(
    private val walletDao: WalletDao,
    private val cryptoStore: CryptoStore
) : WalletRepository {

    override fun wallets(): Flow<List<WalletEntity>> = walletDao.observeAll()

    override suspend fun createNewWallet(derived: Derived): Resource<Unit> {
        val encryptedMnemonic = cryptoStore.encrypt(derived.mnemonic.toByteArray())
        val encryptedPrivateKey = cryptoStore.encrypt(derived.privateKeyHex!!.toByteArray())
        val entity = WalletEntity(
            chain = "Multi-Coin",
            address = derived.address,
            publicKeyHex = derived.publicKeyHex,
            privateKey = encryptedPrivateKey.toString(),
            mnemonic = encryptedMnemonic.toString(),
        )
        return safeCall { walletDao.insert(entity) }

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