package com.softwarecleandevelopment.crypto_chains.bitcoin.data.datasource

import com.softwarecleandevelopment.core.crypto.models.AddressParams
import org.bitcoinj.core.NetworkParameters
import org.bitcoinj.core.SegwitAddress
import org.bitcoinj.crypto.ChildNumber
import org.bitcoinj.crypto.DeterministicKey
import org.bitcoinj.crypto.HDKeyDerivation
import org.bitcoinj.crypto.HDUtils
import org.bitcoinj.params.MainNetParams
import org.bitcoinj.wallet.DeterministicSeed
import java.util.Date
import javax.inject.Inject

class BitcoinDataSourceImpl @Inject constructor() : BitcoinDataSource {
    override fun generateAddress(params: AddressParams): String {
        val networkParameters: NetworkParameters =
            MainNetParams.get()

        val words = params.mnemonic.trim().split(" ")
        val seed = DeterministicSeed(words, null, params.passPhrase, Date().time / 1000)

        val masterKey: DeterministicKey = HDKeyDerivation.createMasterPrivateKey(seed.seedBytes)

        val path84 = HDUtils.parsePath("84H/0H/0H/0/${params.accountIndex}")
        val key84 = deriveKeyFromMaster(masterKey, path84)

        val segwitAddress = SegwitAddress.fromKey(networkParameters, key84).toString()

        return segwitAddress
    }

    fun deriveKeyFromMaster(
        masterKey: DeterministicKey,
        path: List<ChildNumber>
    ): DeterministicKey {
        var key = masterKey
        for (child in path) {
            key = HDKeyDerivation.deriveChildKey(key, child)
        }
        return key
    }
}