package com.softwarecleandevelopment.feature.wallets.domain.usecase

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.core.database.room.models.WalletEntity
import com.softwarecleandevelopment.feature.wallets.domain.repository.WalletsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWalletsUseCase @Inject constructor(
    val walletsRepository: WalletsRepository,
) :
    UseCase<Resource<Flow<List<WalletEntity>>>, Unit>() {
    override suspend fun invoke(params: Unit): Resource<Flow<List<WalletEntity>>> {
        return walletsRepository.getWallets()
    }
}