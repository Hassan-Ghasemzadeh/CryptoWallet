package com.softwarecleandevelopment.crypto_chains.ethereum.domain.repository.remote

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.model.CryptoInfo
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.SendResult
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.SendTokenEvent
import kotlinx.coroutines.flow.Flow
import org.web3j.tx.ChainIdLong
import java.math.BigDecimal
import java.math.BigInteger

interface EthRemoteRepository {
    suspend fun send(
        params: SendTokenEvent
    ): Resource<SendResult>

    suspend fun estimateNetworkFee(
        tokenContractAddress: String? = null
    ): Resource<Pair<BigInteger /*gasPrice*/, BigInteger /*gasLimit*/>>

}