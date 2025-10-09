package com.softwarecleandevelopment.feature.transaction.domain.usecase

import android.util.Base64
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.core.crypto.models.AddressParams
import com.softwarecleandevelopment.core.crypto.models.CoinInfo
import com.softwarecleandevelopment.core.crypto.security.CryptoStore
import com.softwarecleandevelopment.crypto_chains.crypto_info.data.model.Transaction
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.model.TransactionParams
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.repository.CryptoInfoRepository
import com.softwarecleandevelopment.feature.wallet_home.domain.usecases.GetActiveWalletUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetAllTransactionsUseCase @Inject constructor(
    private val repository: CryptoInfoRepository,
    private val coins: List<CoinInfo>,
    private val getActiveWalletUseCase: GetActiveWalletUseCase,
    private val cryptoStore: CryptoStore,
) : UseCase<List<Transaction>, Unit>() {
    override suspend fun invoke(params: Unit): List<Transaction> {
        val allTransactions = mutableListOf<Transaction>()

        for (coin in coins) {
            val result = repository.getTransactions(
                TransactionParams(
                    getAddress(coin.id),
                    coin.name.lowercase()
                )
            )

            // Check for success and handle failure
            if (result !is Resource.Success) {
                // Immediately return emptyList if any fetch fails
                return emptyList()
            }

            // Add the successful data to the accumulating list
            allTransactions.addAll(result.data)
        }

        return allTransactions
    }

    private suspend fun getAddress(coinId: String): String {
        val coin = coins.find { it.id == coinId }

        val activeWalletResource = getActiveWalletUseCase.invoke(Unit)
        val wallet = (activeWalletResource as Resource.Success).data.first()

        val mnemonic = decryptMnemonic(wallet?.mnemonic ?: "")


        val addressParams = AddressParams(
            mnemonic = mnemonic,
            accountIndex = wallet?.id?.toInt() ?: 0,
            passPhrase = "",
        )

        val addressResource = coin?.generator?.invoke(addressParams)

        return (addressResource as Resource.Success).data
    }

    // Private helper to isolate the sensitive, error-prone decryption logic.
    private fun decryptMnemonic(base64Mnemonic: String): String {
        val decryptedByteArray = Base64.decode(base64Mnemonic, Base64.DEFAULT)
        val decryptedMnemonic = cryptoStore.decrypt(decryptedByteArray)
        return decryptedMnemonic.toString(Charsets.UTF_8)
    }

}