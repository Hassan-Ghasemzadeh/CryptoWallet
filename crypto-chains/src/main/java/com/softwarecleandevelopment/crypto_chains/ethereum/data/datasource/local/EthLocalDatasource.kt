package com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.local

import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface EthLocalDatasource {
    fun generateAddress() : Flow<String?>
}