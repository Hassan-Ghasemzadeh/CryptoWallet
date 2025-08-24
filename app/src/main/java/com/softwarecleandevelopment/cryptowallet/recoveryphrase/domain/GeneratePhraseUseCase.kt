package com.softwarecleandevelopment.cryptowallet.recoveryphrase.domain

import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.models.Derived

class GeneratePhraseUseCase(private val repository: PhraseRepository) : UseCase<Derived, Unit>() {
    override suspend fun invoke(params: Unit): Derived {
        return repository.generateWallet()
    }

}