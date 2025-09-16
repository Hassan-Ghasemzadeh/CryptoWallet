package com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.confirm_send

import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.crypto.security.CryptoStore
import com.softwarecleandevelopment.core.database.room.models.WalletEntity
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.SendResult
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.SendState
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.SendTokenEvent
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.usecases.EstimateNetworkFeeUseCase
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.usecases.SendTokenUseCase
import com.softwarecleandevelopment.feature.wallet_home.domain.usecases.GetActiveWalletUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.Utf8
import org.web3j.utils.Convert
import javax.inject.Inject

@HiltViewModel
class SendViewModel @Inject constructor(
    private val sendTokenUseCase: SendTokenUseCase,
    private val getActiveWalletUseCase: GetActiveWalletUseCase,
    private val estimateNetworkFeeUseCase: EstimateNetworkFeeUseCase,
    private val cryptoStore: CryptoStore,
) : ViewModel() {
    private val _state = MutableStateFlow(SendState())
    val state: StateFlow<SendState> = _state.asStateFlow()

    private val _privateKey = MutableStateFlow("")
    val privateKey: StateFlow<String> = _privateKey.asStateFlow()

    fun getPrivateKey() {
        viewModelScope.launch {
            val result = getActiveWalletUseCase.invoke(Unit)
            when (result) {
                is Resource.Error -> {

                }

                is Resource.Loading -> {

                }

                is Resource.Success<Flow<WalletEntity?>> -> {
                    try {
                        result.data.collectLatest {

                            val encodedPrivateKey = it?.privateKey.toString()

                            val encryptedPrivateKeyBytes =
                                Base64.decode(encodedPrivateKey, Base64.DEFAULT)

                            val decryptedKeyBytes = cryptoStore.decrypt(encryptedPrivateKeyBytes)

                            val privateKeyString =
                                Base64.encodeToString(decryptedKeyBytes, Base64.DEFAULT)

                            _privateKey.value = privateKeyString
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    fun estimateFee(tokenContractAddress: String? = null) {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true, error = null) }
                val result = estimateNetworkFeeUseCase.invoke(tokenContractAddress)
                // approximate fee in native currency (wei -> ether)
                if (result is Resource.Success) {
                    val (gasPrice, gasLimit) = result.data
                    val feeWei = gasPrice.multiply(gasLimit)
                    val feeEth = Convert.fromWei(feeWei.toBigDecimal(), Convert.Unit.ETHER)
                    // convert to USD: skipped here (needs external API). We'll show ETH amount.
                    _state.update {
                        it.copy(
                            isLoading = false,
                            networkFeeUsd = "~$feeEth ${if (tokenContractAddress.isNullOrEmpty()) "ETH/BNB" else "ETH/BNB (token)"}"
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun send(
        event: SendTokenEvent
    ) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null, successTransactionHash = null) }
            sendTokenUseCase(
                event
            ).collect { resultWrapper ->
                when (resultWrapper) {
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false, error = resultWrapper.message
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                            )
                        }
                    }

                    is Resource.Success<SendResult> -> {
                        resultWrapper.data.copy(
                            receipt = resultWrapper.data.receipt,
                            transactionHash = resultWrapper.data.transactionHash
                        )
                    }
                }
            }
        }
    }

}
