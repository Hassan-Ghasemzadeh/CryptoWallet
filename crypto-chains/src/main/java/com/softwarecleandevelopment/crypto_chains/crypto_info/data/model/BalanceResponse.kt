package com.softwarecleandevelopment.crypto_chains.crypto_info.data.model

import com.google.gson.annotations.SerializedName

data class BalanceResponse(
    @SerializedName("final_balance") val finalBalance: Long
)
