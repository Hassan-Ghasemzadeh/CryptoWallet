package com.softwarecleandevelopment.cryptowallet.confirmphrase.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwarecleandevelopment.core.database.seed_datastore.SecureSeedStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class ConfirmPhraseViewModel @Inject constructor(
    private val storage: SecureSeedStorage,
) : ViewModel() {


    // This will now be populated asynchronously.
    private val originalWords: MutableList<String> = mutableListOf()

    // Initialize states to an empty list to prevent crashes.
    private val _shuffledWords = mutableStateOf<List<String>>(emptyList())
    val shuffledWords: State<List<String>> = _shuffledWords

    private val _selectedWords = mutableStateOf<List<String>>(emptyList())
    val selectedWords: State<List<String>> = _selectedWords

    private val _wrongItems = mutableStateOf<List<Boolean>>(emptyList())
    val wrongItems: State<List<Boolean>> = _wrongItems

    val isAllCorrect: Boolean
        get() = _selectedWords.value.isNotEmpty() && _selectedWords.value == originalWords

    init {
        getOriginalWords()
    }

    private fun getOriginalWords() {
        try {
            viewModelScope.launch {
                val words = storage.getSeed()?.split(",")[0]?.split(" ")
                if (!words.isNullOrEmpty()) {
                    originalWords.addAll(words.toMutableList())

                    // Now that originalWords is populated, initialize the other state variables.
                    _shuffledWords.value =
                        originalWords.shuffled(Random(System.currentTimeMillis()))
                    _selectedWords.value = List(originalWords.size) { "" }
                    _wrongItems.value = List(originalWords.size) { false }
                }
            }
        } catch (e: Exception) {
            Log.d("Tag", "Error: ${e.message}")
        }
    }

    fun onWordClick(word: String, shuffledIndex: Int) {
        // Check if the clicked word is already selected (e.g., in a wrong state).
        if (shuffledIndex < _wrongItems.value.size && _wrongItems.value[shuffledIndex]) {
            // If the word was previously marked as wrong, a new click should deselect it.
            _wrongItems.value = _wrongItems.value.toMutableList().also {
                it[shuffledIndex] = false // Clear the wrong state
            }
            return // Exit the function, as we've handled the deselect action.
        }

        // Now, proceed with the logic for a new selection.
        val nextCorrectIndex = _selectedWords.value.indexOfFirst { it.isEmpty() }

        // If there are no more empty slots, return.
        if (nextCorrectIndex == -1) {
            return
        }

        // Check if the user's current choice is the correct word for the next slot.
        if (originalWords[nextCorrectIndex] == word) {
            // Correct selection logic.
            val newSelectedWords = _selectedWords.value.toMutableList()
            newSelectedWords[nextCorrectIndex] = word
            _selectedWords.value = newSelectedWords

            val newShuffledWords = _shuffledWords.value.toMutableList()
            newShuffledWords[shuffledIndex] = ""
            _shuffledWords.value = newShuffledWords

        } else {
            // Incorrect selection logic.
            // Mark the clicked item as wrong.
            val newWrongItems = _wrongItems.value.toMutableList()
            if (shuffledIndex < newWrongItems.size) {
                newWrongItems[shuffledIndex] = true
            }
            _wrongItems.value = newWrongItems
        }
    }
}