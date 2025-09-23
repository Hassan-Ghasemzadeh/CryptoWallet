package com.softwarecleandevelopment.crypto_chains.tether.data.datasource

interface TetherDataSource {
    suspend fun generateAddress(mnemonic: String, passPhrase: String?): String
}