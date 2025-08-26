package com.softwarecleandevelopment.feature.wallets.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UpdateWalletEvent(val name: String, val walletId: Long, val mnemonic: String) :
    Parcelable