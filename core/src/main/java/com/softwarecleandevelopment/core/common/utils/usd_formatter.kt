package com.softwarecleandevelopment.core.common.utils

import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

fun formatToUSD(amount: Double, maxDecimalDigits: Int): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US)
    formatter.currency = Currency.getInstance("USD")

    formatter.maximumFractionDigits = maxDecimalDigits

    return formatter.format(amount)
}