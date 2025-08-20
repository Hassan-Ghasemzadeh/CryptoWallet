package com.softwarecleandevelopment.cryptowallet.confirmphrase.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwarecleandevelopment.core.database.WalletSecureStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class ConfirmPhraseViewModel @Inject constructor(
    private val storage: WalletSecureStorage,
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

    private val _wrongItems = mutableStateOf(List(originalWords.size) { false })
    val wrongItems: State<List<Boolean>> = _wrongItems

    val isAllCorrect: Boolean
        get() = _selectedWords.value == originalWords

    fun getOriginalWords() {
        try {
            viewModelScope.launch {
                val words = storage.getWallet()?.split(",")[0]?.split(" ")
                if (!words.isNullOrEmpty()) {
                    originalWords.addAll(words.toMutableList())
                }
            }
        } catch (e: Exception) {
            Log.d("Tag", "Error: ${e.message}")
        }
    }
    fun onWordClick(word: String, shuffledIndex: Int) {
        // Check the correct position of the word
        val correctIndex = originalWords.indexOf(word)

        // Get the word currently at the correct position in the user's selection
        val selectedWord = _selectedWords.value[correctIndex]

        // Check if the user is clicking on the correct word
        if (selectedWord.isEmpty() && shuffledWords.value[shuffledIndex] == originalWords[correctIndex]) {
            // If the slot is empty and the clicked word is correct
            // Move the word from the shuffled list to the selected list
            _selectedWords.value = _selectedWords.value.toMutableList().also {
                it[correctIndex] = word
            }
            _shuffledWords.value = _shuffledWords.value.toMutableList().also {
                it[shuffledIndex] = ""
            }
            // Also, make sure the wrong item at this index is cleared if it was previously set
            _wrongItems.value = _wrongItems.value.toMutableList().also {
                it[shuffledIndex] = false
            }
        } else {
            // If the user clicks on the wrong word or the slot is already filled
            _wrongItems.value = _wrongItems.value.toMutableList().also {
                it[shuffledIndex] = true
            }
        }
    }
}