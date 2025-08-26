package com.softwarecleandevelopment.feature.wallets.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WalletDetailViewModel @Inject constructor() : ViewModel() {

    private val _name = mutableStateOf("")
    val name: State<String> = _name


    fun updateWalletName(name: String) {
        _name.value = name
    }
}