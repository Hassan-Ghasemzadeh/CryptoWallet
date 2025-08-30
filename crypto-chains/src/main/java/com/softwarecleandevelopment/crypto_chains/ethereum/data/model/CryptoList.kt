package com.softwarecleandevelopment.crypto_chains.ethereum.data.model

import com.softwarecleandevelopment.crypto_chains.R
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.CryptoInfo


val initialCryptos = listOf<CryptoInfo>(
    CryptoInfo(
        id = "ethereum",
        symbol = "ETH",
        name = "Ethereum",
        iconRes = R.drawable.ic_eth
    )
)