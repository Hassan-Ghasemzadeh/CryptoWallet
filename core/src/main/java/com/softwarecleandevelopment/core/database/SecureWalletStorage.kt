package com.softwarecleandevelopment.core.database

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.crypto.tink.Aead
import kotlinx.coroutines.flow.first
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
import javax.inject.Singleton

// A custom serializer to handle encryption and decryption
class EncryptedPreferencesSerializer @Inject constructor(private val aead: Aead) :
    Serializer<String> {
    override val defaultValue: String = ""

    override suspend fun readFrom(input: InputStream): String {
        return try {
            val encryptedBytes = input.readBytes()
            val decryptedBytes = aead.decrypt(encryptedBytes, null)
            String(decryptedBytes, Charsets.UTF_8)
        } catch (e: Exception) {
            // Handle corruption, return default value
            throw IOException("Error decrypting data", e)
        }
    }

    override suspend fun writeTo(t: String, output: OutputStream) {
        val decryptedBytes = t.toByteArray(Charsets.UTF_8)
        val encryptedBytes = aead.encrypt(decryptedBytes, null)
        output.write(encryptedBytes)
    }
}

@Singleton
class SecureWalletStorage @Inject constructor(
    private val context: Context,
    private val serializer: EncryptedPreferencesSerializer
) {
    private val prefFileName = "user_wallet.encrypted"
    private val Context.encryptedDataStore: DataStore<String> by dataStore(
        fileName = prefFileName,
        serializer = serializer,
    )

    suspend fun saveWallet(wallet: String) {
        context.encryptedDataStore.updateData {
            wallet
        }
    }

    suspend fun getWallet(): String? {
        val wallet = context.encryptedDataStore.data.first()
        return wallet.ifEmpty { null }
    }

    suspend fun clearWallet() {
        context.encryptedDataStore.updateData {
            ""
        }
    }
}