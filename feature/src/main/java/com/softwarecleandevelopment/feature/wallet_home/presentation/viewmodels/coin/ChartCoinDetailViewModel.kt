package com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.coin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwarecleandevelopment.feature.wallet_home.domain.models.ChartCoin
import com.softwarecleandevelopment.feature.wallet_home.domain.usecases.GetChartCoinDetailUseCase
import com.softwarecleandevelopment.feature.wallet_home.domain.usecases.ObserveLivePricesForUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChartCoinDetailViewModel @Inject constructor(
    private val detailUseCase: GetChartCoinDetailUseCase,
    private val observeLivePricesForUseCase: ObserveLivePricesForUseCase,
) : ViewModel() {

    private val _coin = MutableStateFlow<ChartCoin?>(null)
    val coin: StateFlow<ChartCoin?> = _coin

    private val _livePrice = MutableStateFlow<Double?>(null)
    val livePrice: StateFlow<Double?> = _livePrice


    fun load(coinId: String, asset: String) {
        viewModelScope.launch {
            val detail = detailUseCase.invoke(coinId)
            _coin.value = detail

            observeLivePricesForUseCase.invoke(setOf(asset))
                .map { it[asset] }
                .filterNotNull()
                .onEach { _livePrice.value = it }
                .launchIn(this)
        }
    }
}