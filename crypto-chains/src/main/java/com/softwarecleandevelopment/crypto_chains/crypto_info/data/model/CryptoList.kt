package com.softwarecleandevelopment.crypto_chains.crypto_info.data.model

import com.softwarecleandevelopment.crypto_chains.R
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.model.CryptoInfo


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