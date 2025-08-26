package com.softwarecleandevelopment.feature.wallets.domain.usecase

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.feature.wallets.domain.repository.WalletsRepository
import javax.inject.Inject

class DeleteWalletUseCase @Inject constructor(
    val walletsRepository: WalletsRepository
) : UseCase<Resource<Unit>, Long>() {
    override suspend fun invoke(params: Long): Resource<Unit> {
        return walletsRepository.deleteWallet(params)
    }
}