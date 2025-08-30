package com.softwarecleandevelopment.crypto_chains.ethereum.domain.models

data class GetCryptoInfoEvent(
    val cryptos: List<CryptoInfo>,
    val address: String,
)