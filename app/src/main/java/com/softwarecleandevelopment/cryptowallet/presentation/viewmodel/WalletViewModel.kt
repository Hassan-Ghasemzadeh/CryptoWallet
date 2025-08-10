package com.softwarecleandevelopment.cryptowallet.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.softwarecleandevelopment.cryptowallet.data.WalletRepositoryImpl
import com.softwarecleandevelopment.cryptowallet.domain.GenerateWalletUseCase
import com.softwarecleandevelopment.cryptowallet.util.storage.SecureSeedStorage
import kotlinx.coroutines.launch

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val walletRepository: WalletRepositoryImpl,
    private val storage: SecureSeedStorage,
) : ViewModel() {
    private var _mnemonic = mutableStateOf("")
    val mnemonic: State<String> = _mnemonic
    private var _address = mutableStateOf("")
    val address: State<String> = _address

    fun generateWallet() {
        viewModelScope.launch {
            val generateWalletUseCase = GenerateWalletUseCase(repository = walletRepository)
            val wallet = generateWalletUseCase()
            _mnemonic.value = wallet.mnemonic
            _address.value = wallet.address
            storage.saveSeed(seed = "${wallet.mnemonic},${wallet.address}")
        }
    }
}