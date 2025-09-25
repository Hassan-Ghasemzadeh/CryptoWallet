package com.softwarecleandevelopment.crypto_chains.dogecoin.data.datasource

import com.softwarecleandevelopment.core.common.model.AddressParams
import com.softwarecleandevelopment.core.common.utils.base58CheckEncode
import com.softwarecleandevelopment.core.common.utils.ripemd160
import com.softwarecleandevelopment.core.common.utils.sha256
import org.bitcoinj.crypto.ChildNumber
import org.bitcoinj.crypto.DeterministicHierarchy
import org.bitcoinj.crypto.HDKeyDerivation
import org.bitcoinj.crypto.MnemonicCode
import javax.inject.Inject

class DogeCoinDataSourceImpl @Inject constructor() : DogeCoinDataSource {
    override suspend fun generateAddress(
        params: AddressParams,
    ): String {
        val words = params.mnemonic.split(Regex("\\s+"))
        val seed = MnemonicCode.toSeed(words, params.passPhrase)
        val masterKey = HDKeyDerivation.createMasterPrivateKey(seed)
        val hierarchy = DeterministicHierarchy(masterKey)
        val path: List<ChildNumber> = listOf(
            ChildNumber(44, false),
            ChildNumber(3, false),
            ChildNumber.ZERO_HARDENED,
            ChildNumber.ZERO,
            ChildNumber(params.accountIndex, false),
        )
        val key = hierarchy.get(path, true, true)
        val pubKeyBytes = key.pubKeyPoint.getEncoded(true)
        val sha = sha256(pubKeyBytes)
        val ripe = ripemd160(sha)
        val versionedPayload = byteArrayOf(0x1E.toByte()) + ripe
        return base58CheckEncode(versionedPayload)
    }
}