package com.softwarecleandevelopment.crypto_chains.tether.domain.repository

import com.softwarecleandevelopment.core.common.utils.Resource

interface TetherRepository {
    suspend fun generateAddress(mnemonic: String, passPhrase: String?): Resource<String>
}