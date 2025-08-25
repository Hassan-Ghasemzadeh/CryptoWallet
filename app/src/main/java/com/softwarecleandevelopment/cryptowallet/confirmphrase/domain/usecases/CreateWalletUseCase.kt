package com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.usecases

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.models.Derived
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.repository.WalletRepository
import javax.inject.Inject

class CreateWalletUseCase @Inject constructor(val repository: WalletRepository) :
    UseCase<Resource<Long>, Derived>() {
    override suspend fun invoke(params: Derived): Resource<Long> {
        return repository.createNewWallet(derived = params)
    }
}