package com.softwarecleandevelopment.crypto_chains.ethereum.domain.models

import org.web3j.tx.ChainIdLong
import java.math.BigDecimal

data class SendTokenEvent(
    val privateKey: String,
    val toAddress: String,
    val amountHuman: BigDecimal,
    val tokenContractAddress: String? = null,
    val tokenDecimals: Int = 18,
    val chainId: Long = ChainIdLong.MAINNET
)