package com.softwarecleandevelopment.crypto_chains.bitcoin.data.datasource

import org.bitcoinj.core.Address
import org.bitcoinj.crypto.ChildNumber
import org.bitcoinj.crypto.DeterministicHierarchy
import org.bitcoinj.crypto.HDKeyDerivation
import org.bitcoinj.crypto.HDUtils
import org.bitcoinj.params.MainNetParams
import org.bitcoinj.script.Script
import org.bitcoinj.wallet.DeterministicSeed
import org.bitcoinj.wallet.KeyChainGroup
import java.util.Date
import javax.inject.Inject

class BitcoinDataSourceImpl @Inject constructor() : BitcoinDataSource {
    override fun generateAddress(mnemonic: String, passPhrase: String): String {
        //Establish the network parameters
        val params = MainNetParams.get()
        val words = mnemonic.split(Regex("\\s+"))
        //Derive the seed from the mnemonic and passphrase
        val seed = DeterministicSeed(
            words, null, passPhrase, Date().time / 1000
        )
        //Create a KeyChainGroup with the correct script type
        val keyChainGroup =
            KeyChainGroup.builder(params).fromSeed(seed, Script.ScriptType.P2WPKH).build()
        val hierarchy = DeterministicHierarchy(keyChainGroup.activeKeyChain.watchingKey)
        val accountPath = HDUtils.parsePath("44H/0H/0H")
        //Derive the account key from the key chain's active key
        val accountKey = hierarchy.get(accountPath, false, true)
        //Generate and return the P2WPKH address
        val address = Address.fromKey(params, accountKey, Script.ScriptType.P2WPKH)
        return address.toString()
    }
}