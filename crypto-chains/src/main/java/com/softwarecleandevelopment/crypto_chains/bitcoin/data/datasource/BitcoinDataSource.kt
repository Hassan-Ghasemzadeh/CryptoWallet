package com.softwarecleandevelopment.crypto_chains.bitcoin.data.datasource


interface BitcoinDataSource {
    fun generateAddress(mnemonic: String, passPhrase: String = ""): String
}