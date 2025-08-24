package com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.usecases

import com.softwarecleandevelopment.core.common.UseCase
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.models.Derived
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.models.Result
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.repository.WalletRepository
import javax.inject.Inject

class CreateWalletUseCase @Inject constructor(val repository: WalletRepository) :
    UseCase<Result<Unit>, Derived>() {
    override suspend fun invoke(params: Derived): Result<Unit> {
        return repository.createNewWallet(derived = params)
    }
}