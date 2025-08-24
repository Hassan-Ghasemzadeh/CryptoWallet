package com.softwarecleandevelopment.cryptowallet.confirmphrase.data.source

import com.softwarecleandevelopment.core.crypto.ChainType
import com.softwarecleandevelopment.core.crypto.CryptoStore
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.models.Derived
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.models.Result
import com.softwarecleandevelopment.cryptowallet.confirmphrase.data.models.WalletDao
import com.softwarecleandevelopment.cryptowallet.confirmphrase.data.models.WalletEntity
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.repository.WalletRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WalletRepositoryImpl @Inject constructor(
    private val walletDao: WalletDao,
    private val cryptoStore: CryptoStore
) : WalletRepository {

    override fun wallets(): Flow<List<WalletEntity>> = walletDao.observeAll()

    override suspend fun createNewWallet(derived: Derived): Result<Unit> {
        return try {
            val encryptedMnemonic = cryptoStore.encrypt(derived.mnemonic.toByteArray())
            val encryptedPrivateKey = cryptoStore.encrypt(derived.privateKeyHex!!.toByteArray())
            val entity = WalletEntity(
                chain = "Multi-Coin",
                address = derived.address,
                publicKeyHex = derived.publicKeyHex,
                privateKey = encryptedPrivateKey.toString(),
                mnemonic = encryptedMnemonic.toString(),
            )
            walletDao.insert(entity)
            Result.Success(Unit)
        } catch (exception: Exception) {
            Result.Error(exception)
        }
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