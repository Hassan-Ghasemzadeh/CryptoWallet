package com.softwarecleandevelopment.feature.wallets.domain.usecase

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.feature.wallets.domain.models.UpdateWalletEvent
import com.softwarecleandevelopment.feature.wallets.domain.repository.WalletsRepository
import javax.inject.Inject

class UpdateWalletUseCase @Inject constructor(
    val walletsRepository: WalletsRepository
) : UseCase<Resource<Unit>, UpdateWalletEvent>() {
    override suspend fun invoke(params: UpdateWalletEvent): Resource<Unit> {
        return walletsRepository.updateWalletName(params.name, params.walletId)
    }
}