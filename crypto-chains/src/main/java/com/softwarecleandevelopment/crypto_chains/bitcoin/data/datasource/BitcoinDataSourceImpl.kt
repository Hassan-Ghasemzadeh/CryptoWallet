package com.softwarecleandevelopment.crypto_chains.bitcoin.data.datasource

import com.softwarecleandevelopment.core.crypto.models.AddressParams
import org.bitcoinj.core.Address
import org.bitcoinj.crypto.DeterministicHierarchy
import org.bitcoinj.crypto.HDUtils
import org.bitcoinj.params.MainNetParams
import org.bitcoinj.script.Script
import org.bitcoinj.wallet.DeterministicSeed
import org.bitcoinj.wallet.KeyChainGroup
import java.util.Date
import javax.inject.Inject

class BitcoinDataSourceImpl @Inject constructor() : BitcoinDataSource {
    override fun generateAddress(params: AddressParams): String {
        //Establish the network parameters
        val mainNetParams = MainNetParams.get()
        val words = params.mnemonic.split(Regex("\\s+"))
        //Derive the seed from the mnemonic and passphrase
        val seed = DeterministicSeed(
            words, null, params.passPhrase, Date().time / 1000
        )
        //Create a KeyChainGroup with the correct script type
        val keyChainGroup =
            KeyChainGroup.builder(mainNetParams).fromSeed(seed, Script.ScriptType.P2WPKH).build()
        val hierarchy = DeterministicHierarchy(keyChainGroup.activeKeyChain.watchingKey)
        val accountPath = HDUtils.parsePath("m/44'/0'/0'/0/0")
        //Derive the account key from the key chain's active key
        val accountKey = hierarchy.get(accountPath, false, true)
        //Generate and return the P2WPKH address
        val address = Address.fromKey(mainNetParams, accountKey, Script.ScriptType.P2WPKH)
        return address.toString()
    }
}