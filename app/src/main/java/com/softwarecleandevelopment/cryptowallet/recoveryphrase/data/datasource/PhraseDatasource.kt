package com.softwarecleandevelopment.cryptowallet.recoveryphrase.data.datasource

import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.models.Derived

interface PhraseDatasource {
    suspend fun generateWallet(): Derived
}