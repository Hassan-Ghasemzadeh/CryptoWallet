package com.softwarecleandevelopment.feature.transaction.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.crypto_chains.crypto_info.data.model.Transaction
import com.softwarecleandevelopment.feature.transaction.domain.usecase.GetAllTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val getAllTransactionsUseCase: GetAllTransactionsUseCase,
) : ViewModel() {
    private val _transactions = MutableStateFlow<Resource<List<Transaction>>>(Resource.Loading)
    val transaction: StateFlow<Resource<List<Transaction>>> = _transactions.asStateFlow()
    private val _isRefreshing = MutableStateFlow<Boolean>(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        fetchTransactions()
    }

    fun fetchTransactions() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                val response = getAllTransactionsUseCase.invoke(Unit)
                _transactions.value = Resource.Success(data = response)
            } catch (exception: Exception) {
                _transactions.value = Resource.Error(message = exception.message ?: "Unknown error")
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}