package com.softwarecleandevelopment.cryptowallet.recoveryphrase.domain

import com.softwarecleandevelopment.core.common.UseCase

class GenerateWalletUseCase(private val repository: WalletRepository) : UseCase<Wallet, Unit> {
    override suspend fun invoke(params: Unit): Wallet {
        return repository.generateWallet()
    }

}