package com.softwarecleandevelopment.feature.wallet_home.domain.usecases

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.core.database.room.models.WalletEntity
import com.softwarecleandevelopment.feature.wallets.domain.repository.WalletsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActiveWalletUseCase @Inject constructor(
    private val walletsRepository: WalletsRepository
) : UseCase<Resource<Flow<WalletEntity?>>, Unit>() {
    override suspend fun invoke(params: Unit): Resource<Flow<WalletEntity?>> {
        return walletsRepository.getActiveWallet()
    }

}