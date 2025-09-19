package com.softwarecleandevelopment.crypto_chains.ethereum.data.model

import com.softwarecleandevelopment.crypto_chains.R
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.CryptoInfo


val initialCryptos = listOf<CryptoInfo>(
    CryptoInfo(
        id = "ethereum",
        symbol = "ETH",
        name = "Ethereum",
        iconRes = R.drawable.ic_eth
    ),
    CryptoInfo(
        id = "bitcoin",
        symbol = "BTC",
        name = "Bitcoin",
        iconRes = R.drawable.ic_btc
    ),
    CryptoInfo(
        id = "doge",
        symbol = "DOGE",
        name = "Dogecoin",
        iconRes = R.drawable.ic_doge
    ),
    CryptoInfo(
        id = "tether",
        symbol = "USDT",
        name = "Tether",
        iconRes = R.drawable.ic_usdt
    )
)