package com.softwarecleandevelopment.crypto_chains.crypto_info.data.model

import com.google.gson.annotations.SerializedName

data class NetworkFeeInfo(
    @SerializedName("high_fee_per_kb") val highFeePerKb: Long?,
    @SerializedName("medium_fee_per_kb") val mediumFeePerKb: Long?,
    @SerializedName("low_fee_per_kb") val lowFeePerKb: Long?,
)
