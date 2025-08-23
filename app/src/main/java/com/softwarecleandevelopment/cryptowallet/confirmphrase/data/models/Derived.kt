package com.softwarecleandevelopment.cryptowallet.confirmphrase.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Derived(
    val mnemonic: String,
    val address: String,
    val publicKeyHex: String,
    val privateKeyHex: String?,
    val derivationPath: String
) : Parcelable {
    constructor() : this("", "", "", "", "")
}