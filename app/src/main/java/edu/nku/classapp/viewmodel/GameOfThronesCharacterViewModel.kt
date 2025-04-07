package edu.nku.classapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.nku.classapp.data.model.GameOfThronesApiResponse
import edu.nku.classapp.data.repository.GameOfThronesRepository
import edu.nku.classapp.model.GameOfThronesCharacterResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameOfThronesCharacterViewModel @Inject constructor(
    private val gameOfThronesRepository: GameOfThronesRepository
) : ViewModel() {

    private val _characters =
        MutableStateFlow<GameOfThronesCharacterState>(GameOfThronesCharacterState.Loading)
    val characters: StateFlow<GameOfThronesCharacterState> = _characters.asStateFlow()

    fun fillData() = viewModelScope.launch {

        when (val response = gameOfThronesRepository.getCharacters()) {
            GameOfThronesApiResponse.Error -> _characters.value =
                GameOfThronesCharacterState.Failure

            is GameOfThronesApiResponse.Success -> _characters.value =
                GameOfThronesCharacterState.Success(response.characters)

            is GameOfThronesApiResponse.DetailsSuccess -> {
                _characters.value = GameOfThronesCharacterState.Failure
            }
        }
    }


    sealed class GameOfThronesCharacterState {
        data class Success(val characters: List<GameOfThronesCharacterResponse.Character>) :
            GameOfThronesCharacterState()

        data object Failure : GameOfThronesCharacterState()
        data object Loading : GameOfThronesCharacterState()
    }

}