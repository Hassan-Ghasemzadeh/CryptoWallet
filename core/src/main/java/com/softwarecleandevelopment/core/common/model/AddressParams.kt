package com.softwarecleandevelopment.core.common.model

data class AddressParams(
    val mnemonic: String,
    val passPhrase: String?,
    val accountIndex: Int,
)