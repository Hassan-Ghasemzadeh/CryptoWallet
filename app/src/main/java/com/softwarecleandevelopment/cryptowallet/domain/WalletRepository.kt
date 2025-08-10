package com.softwarecleandevelopment.cryptowallet.domain

import org.kethereum.bip32.model.Seed

interface WalletRepository {
    suspend fun generateWallet(): Wallet
}