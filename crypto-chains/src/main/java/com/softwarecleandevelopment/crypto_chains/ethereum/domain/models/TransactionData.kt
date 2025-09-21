package com.softwarecleandevelopment.crypto_chains.ethereum.domain.models

import org.web3j.crypto.RawTransaction
import java.math.BigInteger

/**
 * A data class to encapsulate the raw transaction and value.
 */
data class TransactionData(
    val rawTx: RawTransaction,
    val value: BigInteger
)
