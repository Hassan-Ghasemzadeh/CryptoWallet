package com.softwarecleandevelopment.cryptowallet.recoveryphrase.domain

import com.softwarecleandevelopment.cryptowallet.confirmphrase.data.models.Derived

interface PhraseRepository {
    suspend fun generateWallet(): Derived
}