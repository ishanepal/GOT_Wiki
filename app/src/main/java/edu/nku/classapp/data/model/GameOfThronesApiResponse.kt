package edu.nku.classapp.data.model

import edu.nku.classapp.model.GameOfThronesCharacterResponse

sealed class GameOfThronesApiResponse {
    data class Success(val characters: List<GameOfThronesCharacterResponse.Character>) :
        GameOfThronesApiResponse()

    data class DetailsSuccess(val character: GameOfThronesCharacterResponse.Character) :
        GameOfThronesApiResponse()

    data object Error : GameOfThronesApiResponse()
}