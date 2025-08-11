package com.softwarecleandevelopment.cryptowallet.confirmphrase.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.util.storage.SecureSeedStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class ConfirmPhraseViewModel @Inject constructor(
    private val storage: SecureSeedStorage,
) : ViewModel() {
    private val originalWords: MutableList<String> = mutableListOf()

    init {
        getOriginalWords()
    }

    private val _shuffledWords =
        mutableStateOf(originalWords.shuffled(Random(System.currentTimeMillis())))
    val shuffledWords: State<List<String>> = _shuffledWords

    private val _selectedWords = mutableStateOf(List(originalWords.size) { "" })
    val selectedWords: State<List<String>> = _selectedWords

    private val _wrongItems = mutableStateOf(MutableList(originalWords.size) { false })
    val wrongItems: State<List<Boolean>> = _wrongItems

    val isAllCorrect: Boolean
        get() = _selectedWords.value == originalWords

    fun getOriginalWords() {
        try {
            viewModelScope.launch {
                val words = storage.getSeed()?.split(",")[0]?.split(" ")
                if (!words.isNullOrEmpty()) {
                    originalWords.addAll(words.toMutableList())
                }
            }
        } catch (e: Exception) {
            Log.d("Tag", "Error: ${e.message}")
        }
    }

    fun onWordClick(word: String, shuffledIndex: Int) {
        val correctIndex = originalWords.indexOf(word)

        if (_wrongItems.value[shuffledIndex]) {
            _wrongItems.value = _wrongItems.value.toMutableList().also {
                it[shuffledIndex] = false
            }
        } else {
            if (_selectedWords.value[correctIndex].isEmpty()) {
                _selectedWords.value = _selectedWords.value.toMutableList().also {
                    it[correctIndex] = word
                }
                _shuffledWords.value = _shuffledWords.value.toMutableList().also {
                    it[shuffledIndex] = ""
                }
            } else {
                _wrongItems.value = _wrongItems.value.toMutableList().also {
                    it[shuffledIndex] = true
                }
            }
        }
    }
}