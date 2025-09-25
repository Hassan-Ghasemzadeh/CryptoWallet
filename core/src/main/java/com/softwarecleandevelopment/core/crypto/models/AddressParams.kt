package com.softwarecleandevelopment.core.crypto.models

data class AddressParams(
    val mnemonic: String,
    val passPhrase: String?,
    val accountIndex: Int,
)