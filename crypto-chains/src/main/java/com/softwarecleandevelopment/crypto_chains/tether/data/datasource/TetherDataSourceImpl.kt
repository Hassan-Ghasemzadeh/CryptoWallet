package com.softwarecleandevelopment.crypto_chains.tether.data.datasource

import com.softwarecleandevelopment.core.crypto.models.AddressParams
import org.web3j.crypto.Bip32ECKeyPair
import org.web3j.crypto.Credentials
import org.web3j.crypto.MnemonicUtils
import javax.inject.Inject

private const val HARDEDED_BIT: Int = -0x80000000

class TetherDataSourceImpl @Inject constructor() : TetherDataSource {
    override suspend fun generateAddress(
        params: AddressParams,
    ): String {
        val seed = MnemonicUtils.generateSeed(params.mnemonic, params.passPhrase)
        val masterKeyPair = Bip32ECKeyPair.generateKeyPair(seed)
        val path = intArrayOf(
            44 or HARDEDED_BIT,
            60 or HARDEDED_BIT,
            0 or HARDEDED_BIT,
            0,
            params.accountIndex,
        )
        val derivedKeyPair = Bip32ECKeyPair.deriveKeyPair(masterKeyPair, path)
        val credentials = Credentials.create(derivedKeyPair)
        val address = credentials.address
        return address
    }
}