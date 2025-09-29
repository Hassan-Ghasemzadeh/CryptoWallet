package com.softwarecleandevelopment.crypto_chains.dogecoin.data.datasource

import com.softwarecleandevelopment.core.common.utils.FeeEstimator
import javax.inject.Inject
import kotlin.math.ceil

class DogecoinFeeEstimator @Inject constructor() : FeeEstimator {
    override fun estimateFee(feeRate: Long, address: String): Double {
        val txSize = estimateTxSize()
        val kbSize = ceil(txSize / 1000.0)
        val fee = (feeRate * kbSize) / 100_000_000.0
        return fee
    }

    private fun estimateTxSize(): Int {
        val input = 1
        val output = 1
        return input * 180 + output * 34 + 10
    }
}