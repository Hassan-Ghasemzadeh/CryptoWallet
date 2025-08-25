package com.softwarecleandevelopment.core.database.room.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "wallets", indices = [Index(value = ["chain", "address"], unique = true)]
)
data class WalletEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val chain: String,
    val address: String,
    val publicKeyHex: String?,
    val privateKey: String,
    val mnemonic: String,
    val createdAt: Long = System.currentTimeMillis(),
    val isActive: Boolean = false,
)