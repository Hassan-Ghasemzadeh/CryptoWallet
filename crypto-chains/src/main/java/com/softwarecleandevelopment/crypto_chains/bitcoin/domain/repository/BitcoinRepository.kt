package com.softwarecleandevelopment.crypto_chains.bitcoin.domain.repository

import com.softwarecleandevelopment.core.common.utils.Resource

interface BitcoinRepository {
    suspend fun generateBitcoinAddress(mnemonic: String, passPhrase: String): Resource<String>
}