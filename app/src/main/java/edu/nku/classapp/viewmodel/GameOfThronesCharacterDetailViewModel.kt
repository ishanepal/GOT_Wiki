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
class GameOfThronesCharacterDetailViewModel @Inject constructor(
    private val gameOfThronesRepository: GameOfThronesRepository
) : ViewModel() {

    private val _charactersDetail =
        MutableStateFlow<GameOfThronesCharacterDetailState>(GameOfThronesCharacterDetailState.Loading)
    val charactersDetail: StateFlow<GameOfThronesCharacterDetailState> =
        _charactersDetail.asStateFlow()

    fun fetchCharacterDetail(workId: Int) = viewModelScope.launch {
        _charactersDetail.value = GameOfThronesCharacterDetailState.Loading
        when (val response = gameOfThronesRepository.getCharacterDetail(workId)) {
            is GameOfThronesApiResponse.DetailsSuccess -> {
                _charactersDetail.value =
                    GameOfThronesCharacterDetailState.Success(response.character)
            }

            else -> {
                _charactersDetail.value = GameOfThronesCharacterDetailState.Failure
            }
        }
    }

    sealed class GameOfThronesCharacterDetailState {
        data class Success(val character: GameOfThronesCharacterResponse.Character) :
            GameOfThronesCharacterDetailState()

        data object Failure : GameOfThronesCharacterDetailState()
        data object Loading : GameOfThronesCharacterDetailState()
    }

}