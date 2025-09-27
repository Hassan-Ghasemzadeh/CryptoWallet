package com.softwarecleandevelopment.core.common.utils

import org.bouncycastle.crypto.digests.RIPEMD160Digest
import java.math.BigInteger
import java.security.MessageDigest

const val BASE58_ALPHABET = "123456789ABCDEFGHIJKLMNOPQRSTUVWxYZabcdefghijklmnopqrstuvwxyz"

fun sha256(input: ByteArray): ByteArray = MessageDigest.getInstance("SHA-256").digest(input)

fun ripemd160(input: ByteArray): ByteArray {
    val digest = RIPEMD160Digest()
    digest.update(input, 0, input.size)
    val out = ByteArray(20)
    digest.doFinal(out, 0)
    return out
}

fun base58CheckEncode(versioned: ByteArray): String {
    val checksum = sha256(sha256(versioned)).copyOfRange(0, 4)
    val full = versioned + checksum

    var bi = BigInteger(1, full)
    val sb = StringBuilder()
    while (bi > BigInteger.ZERO) {
        val divRem = bi.divideAndRemainder(BigInteger.valueOf(58))
        bi = divRem[0]
        sb.append(BASE58_ALPHABET[divRem[1].toInt()])
    }

    for (b in full) {
        if (b.toInt() == 0) sb.append('1') else break
    }

    return sb.reverse().toString()
}