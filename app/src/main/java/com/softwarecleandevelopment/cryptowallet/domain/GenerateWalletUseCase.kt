package com.softwarecleandevelopment.cryptowallet.domain

class GenerateWalletUseCase(private val repository: WalletRepository) {
    suspend operator fun invoke(): Wallet {
        return repository.generateWallet()
    }
}