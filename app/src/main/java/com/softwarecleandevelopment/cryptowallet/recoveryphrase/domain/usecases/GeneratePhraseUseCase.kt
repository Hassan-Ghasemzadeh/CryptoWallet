package com.softwarecleandevelopment.cryptowallet.recoveryphrase.domain.usecases

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.models.Derived
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.domain.repository.PhraseRepository

class GeneratePhraseUseCase(private val repository: PhraseRepository) : UseCase<Resource<Derived>, Unit>() {
    override suspend fun invoke(params: Unit): Resource<Derived> {
        return repository.generateWallet()
    }

}