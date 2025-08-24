package com.softwarecleandevelopment.feature.dashboard.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.softwarecleandevelopment.feature.dashboard.domain.models.BottomNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf

@HiltViewModel
class BottomNavigationViewModel @Inject constructor() : ViewModel() {
    private val _selectedIndex = mutableIntStateOf(BottomNavigation.HOME.ordinal)
    val selectedIndex: State<Int> = _selectedIndex

    fun isSelected(index: Int): Boolean {
        return selectedIndex.value == index
    }

    fun navigate(index: Int) {
        _selectedIndex.intValue = index
    }
}