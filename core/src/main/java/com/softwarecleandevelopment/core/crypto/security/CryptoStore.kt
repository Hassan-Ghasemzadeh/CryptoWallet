package com.softwarecleandevelopment.core.crypto.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject

class CryptoStore @Inject constructor() {
    private val alias: String = "wallet_master_key"
    private fun getOrCreateKey(): SecretKey? {
        try {
            val ks = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }

            // Check if the key already exists and return it.
            (ks.getKey(alias, null) as? SecretKey)?.let { return it }

            // If the key doesn't exist, generate a new one.
            val gen = KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES,
                "AndroidKeyStore"
            )
            val spec = KeyGenParameterSpec.Builder(
                alias,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setUserAuthenticationRequired(false)
                .build()

            gen.init(spec)
            return gen.generateKey()
        } catch (e: Exception) {
            // Handle any exceptions during key generation or retrieval
            // and return null.
            return null
        }
    }

    fun encrypt(plain: ByteArray): ByteArray {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, getOrCreateKey())
        val iv = cipher.iv // 12 bytes
        val data = cipher.doFinal(plain)
        return iv + data // iv || ciphertext+tag
    }

    fun decrypt(blob: ByteArray): ByteArray {
        val iv = blob.copyOfRange(0, 12)
        val data = blob.copyOfRange(12, blob.size)
        val key = getOrCreateKey()

        // Add a null-check to prevent the crash
        if (key == null) {
            throw IllegalStateException("The encryption key could not be retrieved.")
        }
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, key, GCMParameterSpec(128, iv))
        return cipher.doFinal(data)
    }
}