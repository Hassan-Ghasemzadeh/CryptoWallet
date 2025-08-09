package com.softwarecleandevelopment.cryptowallet.data

import android.util.Log
import com.softwarecleandevelopment.cryptowallet.domain.Wallet
import com.softwarecleandevelopment.cryptowallet.domain.WalletRepository
import org.kethereum.bip39.dirtyPhraseToMnemonicWords
import org.kethereum.bip39.generateMnemonic
import org.kethereum.bip39.toSeed
import org.kethereum.bip39.wordlists.WORDLIST_ENGLISH
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WalletRepositoryImpl @Inject constructor() : WalletRepository {
    @OptIn(ExperimentalStdlibApi::class)
    override suspend fun generateWallet(): Wallet {
        return runCatching {
            val mnemonic = generateMnemonic(wordList = WORDLIST_ENGLISH)
            val mnemonicWords = dirtyPhraseToMnemonicWords(mnemonic)
            val seed = mnemonicWords.toSeed()
            Wallet(mnemonicWords.words.joinToString(" "), seed.seed.toHexString())
        }.getOrElse { exception ->
            Log.d("generateWallet", "Error Message: ${exception.message}", exception)
            Wallet("", "")
        }
    }
}