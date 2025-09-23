package com.softwarecleandevelopment.crypto_chains.dogecoin.data.datasource

interface DogeCoinDataSource {
   suspend fun generateAddress(mnemonic: String, passPhrase: String): String
}