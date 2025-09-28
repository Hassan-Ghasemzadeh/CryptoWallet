package com.softwarecleandevelopment.core.common.utils

interface BalanceUseCase {
    suspend fun invoke(params: String): Double
}