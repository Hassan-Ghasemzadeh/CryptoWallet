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
        val params = MainNetParams.get()
        val words = mnemonic.split(Regex("\\s+"))
        val seed = DeterministicSeed(
            words, null, passPhrase, Date().time / 1000
        )
        val keyChainGroup =
            KeyChainGroup.builder(params).fromSeed(seed, Script.ScriptType.P2WPKH).build()
        val hierarchy = DeterministicHierarchy(keyChainGroup.activeKeyChain.watchingKey)
        val accountPath = HDUtils.parsePath("44H/0H/0H")
        val accountKey = hierarchy.get(accountPath, false, true)
        val address = Address.fromKey(params, accountKey, Script.ScriptType.P2WPKH)
        return address.toString()
    }
}