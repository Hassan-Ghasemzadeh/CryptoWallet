package com.softwarecleandevelopment.core.di.modules

import android.content.Context
import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeyTemplates
import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import com.softwarecleandevelopment.core.database.EncryptedPreferencesSerializer
import com.softwarecleandevelopment.core.database.SecureWalletStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EncryptionModule {
    @Provides
    @Singleton
    fun provideAead(@ApplicationContext context: Context): Aead {
        AeadConfig.register() // Ensure Tink is initialized
        val keysetHandle: KeysetHandle =
            AndroidKeysetManager.Builder().withSharedPref(context, "master_keyset", "secure_prefs")
                .withKeyTemplate(KeyTemplates.get("AES256_GCM"))
                .withMasterKeyUri("android-keystore://master_key").build().keysetHandle
        return keysetHandle.getPrimitive(Aead::class.java)
    }

    @Provides
    @Singleton
    fun provideEncryptedPreferencesSerializer(aead: Aead): EncryptedPreferencesSerializer {
        return EncryptedPreferencesSerializer(aead)
    }

    @Provides
    @Singleton
    fun provideSecureSeedStorage(
        @ApplicationContext context: Context,
        serializer: EncryptedPreferencesSerializer
    ): SecureWalletStorage {
        return SecureWalletStorage(context = context, serializer = serializer)
    }
}