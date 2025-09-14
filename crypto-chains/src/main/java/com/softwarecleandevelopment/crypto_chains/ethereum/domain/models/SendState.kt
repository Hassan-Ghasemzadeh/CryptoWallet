package com.softwarecleandevelopment.crypto_chains.ethereum.domain.models

data class SendState(
    val isLoading: Boolean = false,
    val networkFeeUsd: String? = null,
    val error: String? = null,
    val successTransactionHash: String? = null
)
