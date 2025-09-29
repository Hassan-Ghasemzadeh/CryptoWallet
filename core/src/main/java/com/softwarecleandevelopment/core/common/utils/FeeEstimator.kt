package com.softwarecleandevelopment.core.common.utils

interface FeeEstimator {
    fun estimateFee(feeRate: Long, address: String): Double
}