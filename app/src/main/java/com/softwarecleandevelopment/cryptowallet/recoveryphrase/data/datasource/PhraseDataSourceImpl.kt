package com.softwarecleandevelopment.cryptowallet.recoveryphrase.data.datasource

import android.util.Log
import com.softwarecleandevelopment.core.database.seed_datastore.SecureSeedStorage
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.models.Derived
import org.kethereum.DEFAULT_ETHEREUM_BIP44_PATH
import org.kethereum.bip32.model.ExtendedKey
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
class PhraseDataSourceImpl @Inject constructor(
    private val storage: SecureSeedStorage,
) : PhraseDatasource {
    override suspend fun generateWallet(): Derived {
        return try {
            val mnemonicWords = generateMnemonicWords()
            val masterKey = generateMasterKey(mnemonicWords = mnemonicWords)
            val words = mnemonicWords.words.joinToString(" ")
            val privateKey = masterKey.keyPair.privateKey.toString()
            storage.saveSeed(wallet = "$words,$privateKey")
            Derived(
                mnemonic = words,
                address = masterKey.keyPair.toAddress().hex,
                publicKeyHex = masterKey.keyPair.publicKey.toString(),
                privateKeyHex = masterKey.keyPair.privateKey.key.toString(),
                derivationPath = ""
            )
        } catch (exception: Exception) {
            Log.d("generateWallet", "Error Message: ${exception.message}", exception)
            Derived(
                mnemonic = "",
                address = "",
                publicKeyHex = "",
                privateKeyHex = "",
                derivationPath = ""
            )
        }
    }

    private fun generateMnemonicWords(): MnemonicWords {
        val mnemonic = generateMnemonic(wordList = WORDLIST_ENGLISH)
        return dirtyPhraseToMnemonicWords(mnemonic)
    }

    private fun generateMasterKey(mnemonicWords: MnemonicWords): ExtendedKey {
        val seed = mnemonicWords.toSeed()
        return seed.toKey(DEFAULT_ETHEREUM_BIP44_PATH)
    }
}