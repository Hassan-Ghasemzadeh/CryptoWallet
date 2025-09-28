package com.softwarecleandevelopment.crypto_chains.dogecoin.data.datasource

import com.softwarecleandevelopment.core.common.utils.base58CheckEncode
import com.softwarecleandevelopment.core.common.utils.ripemd160
import com.softwarecleandevelopment.core.common.utils.sha256
import com.softwarecleandevelopment.core.common.utils.toCoin
import com.softwarecleandevelopment.core.crypto.models.AddressParams
import org.bitcoinj.crypto.ChildNumber
import org.bitcoinj.crypto.DeterministicHierarchy
import org.bitcoinj.crypto.DeterministicKey
import org.bitcoinj.crypto.HDKeyDerivation
import org.bitcoinj.crypto.MnemonicCode
import javax.inject.Inject

class DogeCoinDataSourceImpl @Inject constructor(
    private val api: DogecoinApi,
) : DogeCoinDataSource {
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

        val hash160 = ripemd160(sha256(pubKeyCompressed)) //20 bytes

        val DOGE_VERSION_BYTE: Byte = 0x1E.toByte()
        val versioned = byteArrayOf(DOGE_VERSION_BYTE) + hash160 //21 bytes

        return base58CheckEncode(versioned)
    }

    override suspend fun getDogeCoinBalance(address: String): Double {
        val response = api.getDogeCoinBalance(address)
        val doge = response.finalBalance
        return toCoin(doge).toDouble()
    }

}