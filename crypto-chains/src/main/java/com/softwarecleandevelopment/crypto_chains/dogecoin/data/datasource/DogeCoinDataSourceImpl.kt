package com.softwarecleandevelopment.crypto_chains.dogecoin.data.datasource

import com.softwarecleandevelopment.core.common.utils.base58CheckEncode
import com.softwarecleandevelopment.core.common.utils.ripemd160
import com.softwarecleandevelopment.core.common.utils.sha256
import com.softwarecleandevelopment.core.crypto.models.AddressParams
import org.bitcoinj.crypto.ChildNumber
import org.bitcoinj.crypto.DeterministicHierarchy
import org.bitcoinj.crypto.DeterministicKey
import org.bitcoinj.crypto.HDKeyDerivation
import org.bitcoinj.crypto.MnemonicCode
import javax.inject.Inject

class DogeCoinDataSourceImpl @Inject constructor() : DogeCoinDataSource {
    override suspend fun generateAddress(
        params: AddressParams,
    ): String {
        val mnemonicWords = params.mnemonic.split(" ")

        MnemonicCode.INSTANCE.check(mnemonicWords)

        val seed = MnemonicCode.toSeed(mnemonicWords, params.passPhrase)

        val masterKey = HDKeyDerivation.createMasterPrivateKey(seed)
        val hierarchy = DeterministicHierarchy(masterKey)

        val path = listOf(
            ChildNumber(44, true),
            ChildNumber(3, true),
            ChildNumber(0, true),
            ChildNumber(0, false),
            ChildNumber(params.accountIndex, false)
        )

        val key: DeterministicKey = hierarchy.get(path, false, true)

        val pubKeyCompressed = key.pubKeyPoint.getEncoded(true)
        val hash160 = ripemd160(sha256(pubKeyCompressed))
        val versioned = byteArrayOf(0x1E.toByte()) + hash160

        return base58CheckEncode(versioned)
    }
}