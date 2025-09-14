package com.softwarecleandevelopment.crypto_chains.ethereum.domain.models

import org.web3j.protocol.core.methods.response.TransactionReceipt

data class SendResult(val transactionHash: String, val receipt: TransactionReceipt?)