package com.softwarecleandevelopment.core.common.utils

import java.math.BigInteger
import java.security.MessageDigest

const val BASE58_ALPHABET = "123456789ABCDEFGHIJKLMNOPQRSTUVWxYZabcdefghijklmnopqrstuvwxyz"

fun sha256(input: ByteArray): ByteArray = MessageDigest.getInstance("SHA-256").digest(input)

fun ripemd160(input: ByteArray): ByteArray {
    val digest = MessageDigest.getInstance("RIPEMD160")
    return digest.digest(input)
}

fun base58CheckEncode(versionedPayload: ByteArray): String {
    val checksum = sha256(sha256(versionedPayload)).copyOfRange(0, 4)
    val full = versionedPayload + checksum
    var bigInteger = full.fold(BigInteger.ZERO) { acc, byte ->
        acc.shiftLeft(8).or(BigInteger.valueOf((byte.toInt() and 0xFF).toLong()))
    }
    val stringBuilder = StringBuilder()
    while (bigInteger > BigInteger.ZERO) {
        val divRem = bigInteger.divideAndRemainder(BigInteger.valueOf(58))
        bigInteger = divRem[0]
        val rem = divRem[1].toInt()
        stringBuilder.append(BASE58_ALPHABET[rem])
    }
    //leading-zero->'1'
    for (byte in full) {
        if (byte.toInt() == 0) {
            stringBuilder.append('1')
        } else {
            break
        }
    }
    return stringBuilder.reverse().toString()
}