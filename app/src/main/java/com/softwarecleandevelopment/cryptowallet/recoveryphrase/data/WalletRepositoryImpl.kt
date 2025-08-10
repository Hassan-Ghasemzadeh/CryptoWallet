package com.softwarecleandevelopment.cryptowallet.recoveryphrase.data

import android.util.Log
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.domain.Wallet
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.domain.WalletRepository
import org.kethereum.DEFAULT_ETHEREUM_BIP44_PATH
import org.kethereum.bip32.model.Seed
import org.kethereum.bip32.toKey
import org.kethereum.bip39.dirtyPhraseToMnemonicWords
import org.kethereum.bip39.generateMnemonic
import org.kethereum.bip39.toSeed
import org.kethereum.bip39.wordlists.WORDLIST_ENGLISH
import org.kethereum.crypto.toAddress
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalStdlibApi::class)
@Singleton
class WalletRepositoryImpl @Inject constructor() : WalletRepository {
    override suspend fun generateWallet(): Wallet {
        return runCatching {
            val mnemonic = generateMnemonic(wordList = WORDLIST_ENGLISH)
            val mnemonicWords = dirtyPhraseToMnemonicWords(mnemonic)
            val seed = mnemonicWords.toSeed()
            val wallet = generateCoinAddress(seed = seed)
            Wallet(mnemonicWords.words.joinToString(" "), wallet)
        }.getOrElse { exception ->
            Log.d("generateWallet", "Error Message: ${exception.message}", exception)
            Wallet("", "")
        }
    }

    private fun generateCoinAddress(seed: Seed): String {
        return runCatching {
            val masterKey = seed.toKey(DEFAULT_ETHEREUM_BIP44_PATH)
            val keyPair = masterKey.keyPair
            val wallet = keyPair.toAddress().toString()
            wallet
        }.getOrElse {
            Log.d("getWalletFromSeed", "Error Message: ${it.message}", it)
            ""
        }
    }


}