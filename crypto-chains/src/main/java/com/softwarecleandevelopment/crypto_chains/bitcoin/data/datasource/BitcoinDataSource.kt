package com.softwarecleandevelopment.crypto_chains.bitcoin.data.datasource

import com.softwarecleandevelopment.core.common.model.AddressParams


interface BitcoinDataSource {
    fun generateAddress(params: AddressParams): String
}