package com.softwarecleandevelopment.cryptowallet.recoveryphrase.data.repository

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.safeCall
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.models.Derived
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.data.datasource.PhraseDataSourceImpl
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.domain.repository.PhraseRepository
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalStdlibApi::class)
@Singleton
class PhraseRepositoryImpl @Inject constructor(
    private val dataSourceImpl: PhraseDataSourceImpl,
) : PhraseRepository {
    override suspend fun generateWallet(): Resource<Derived> {
        return safeCall { dataSourceImpl.generateWallet() }
    }

}