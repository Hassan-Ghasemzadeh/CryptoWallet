package com.softwarecleandevelopment.crypto_chains.dogecoin.domian.repository

import com.softwarecleandevelopment.core.common.utils.Resource

interface DogeCoinRepository {
   suspend fun generateAddress(mnemonic: String, passPhrase: String): Resource<String>
}