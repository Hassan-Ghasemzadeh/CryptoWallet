package com.softwarecleandevelopment.cryptowallet.domain

interface WalletRepository {
    suspend fun generateWallet(): Wallet
}