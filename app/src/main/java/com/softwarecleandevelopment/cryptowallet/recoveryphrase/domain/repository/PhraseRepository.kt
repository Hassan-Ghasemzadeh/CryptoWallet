package com.softwarecleandevelopment.cryptowallet.recoveryphrase.domain.repository

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.models.Derived

interface PhraseRepository {
    suspend fun generateWallet(): Resource<Derived>
}