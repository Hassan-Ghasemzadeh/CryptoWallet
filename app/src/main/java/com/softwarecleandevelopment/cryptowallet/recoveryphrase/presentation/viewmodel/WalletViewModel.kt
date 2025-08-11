package com.softwarecleandevelopment.cryptowallet.recoveryphrase.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.data.WalletRepositoryImpl
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.domain.GenerateWalletUseCase
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.util.storage.SecureSeedStorage
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
    private var _phraseList = mutableStateOf(listOf<String>())
    val phraseList: State<List<String>> = _phraseList

    init {
        generateWallet()
    }

    fun generateWallet() {
        viewModelScope.launch {
            val generateWalletUseCase = GenerateWalletUseCase(repository = walletRepository)
            val wallet = generateWalletUseCase()
            _mnemonic.value = wallet.mnemonic
            _address.value = wallet.address
            _phraseList.value = wallet.mnemonic.split(" ")
        }
    }

    fun saveSeed() {
        viewModelScope.launch {
            storage.saveSeed(seed = "${mnemonic},${address}")
        }
    }
}