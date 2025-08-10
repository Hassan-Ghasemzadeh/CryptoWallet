package com.softwarecleandevelopment.cryptowallet.recoveryphrase.domain

interface WalletRepository {
    suspend fun generateWallet(): Wallet
}