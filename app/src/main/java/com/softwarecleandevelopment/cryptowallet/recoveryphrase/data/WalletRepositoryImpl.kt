package com.softwarecleandevelopment.cryptowallet.recoveryphrase.data

import android.util.Log
import com.softwarecleandevelopment.core.database.WalletSecureStorage
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.domain.Wallet
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.domain.WalletRepository
import org.kethereum.DEFAULT_ETHEREUM_BIP44_PATH
import org.kethereum.bip32.model.ExtendedKey
import org.kethereum.bip32.model.Seed
import org.kethereum.bip32.toKey
import org.kethereum.bip39.dirtyPhraseToMnemonicWords
import org.kethereum.bip39.generateMnemonic
import org.kethereum.bip39.model.MnemonicWords
import org.kethereum.bip39.toSeed
import org.kethereum.bip39.wordlists.WORDLIST_ENGLISH
import org.kethereum.crypto.toAddress
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalStdlibApi::class)
@Singleton
class WalletRepositoryImpl @Inject constructor(
    private val storage: WalletSecureStorage,
) : WalletRepository {
    override suspend fun generateWallet(): Wallet {
        return runCatching {
            val mnemonicWords = generateMnemonicWords()
            val masterKey = generateMasterKey(mnemonicWords = mnemonicWords)
            val words = mnemonicWords.words.joinToString(" ")
            val privateKey = masterKey.keyPair.privateKey.toString()
            storage.saveWallet(wallet = "$words,$privateKey")
            Wallet(words, privateKey)
        }.getOrElse { exception ->
            Log.d("generateWallet", "Error Message: ${exception.message}", exception)
            Wallet("", "")
        }
    }

    private fun generateMnemonicWords(): MnemonicWords {
        val mnemonic = generateMnemonic(wordList = WORDLIST_ENGLISH)
        val mnemonicWords = dirtyPhraseToMnemonicWords(mnemonic)
        return mnemonicWords
    }

    private fun generateMasterKey(mnemonicWords: MnemonicWords): ExtendedKey {
        val seed = mnemonicWords.toSeed()
        val masterKey = seed.toKey(DEFAULT_ETHEREUM_BIP44_PATH)
        return masterKey
    }

    private fun generateCoinAddress(mnemonicWords: MnemonicWords): String {
        return runCatching {
            val masterKey = generateMasterKey(mnemonicWords)
            masterKey.keyPair.toAddress().toString()
        }.getOrElse {
            Log.d("getWalletFromSeed", "Error Message: ${it.message}", it)
            ""
        }
    }


}