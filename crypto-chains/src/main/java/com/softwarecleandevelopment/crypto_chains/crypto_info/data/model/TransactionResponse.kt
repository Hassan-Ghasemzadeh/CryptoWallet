package com.softwarecleandevelopment.crypto_chains.crypto_info.data.model

data class TransactionResponse(
    val address: String?,
    val total_received: Long?,
    val total_sent: Long?,
    val balance: Long?,
    val unconfirmed_balance: Long?,
    val final_balance: Long?,
    val txs: List<Transaction>
)

data class Transaction(
    val hash: String,
    val confirmation: Int?,
    val confirmed: String?,
    val total: Long?,
    val fees: Long?,
    val inputs: List<Input>?,
    val outputs: List<Output>?
)

data class Input(
    val addresses: List<String>?,
    val output_value: Long?,
)

data class Output(
    val addresses: List<String>?,
    val value: Long?,
)
