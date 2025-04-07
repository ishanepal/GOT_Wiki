package edu.nku.classapp.data.repository

import edu.nku.classapp.data.GameOfThronesApi
import edu.nku.classapp.data.model.GameOfThronesApiResponse
import javax.inject.Inject

class GameOfThronesRepositoryReal @Inject constructor(
    private val gameOfThronesApi: GameOfThronesApi
) : GameOfThronesRepository {
    override suspend fun getCharacters(): GameOfThronesApiResponse {
        val result = gameOfThronesApi.getCharacters()
        return if (result.isSuccessful) {
            result.body()?.let {
                GameOfThronesApiResponse.Success(it)
            } ?: GameOfThronesApiResponse.Error
        } else {
            GameOfThronesApiResponse.Error
        }
    }

    override suspend fun getCharacterDetail(workId: Int): GameOfThronesApiResponse {
        val result = gameOfThronesApi.getCharacterDetail(workId)
        return if (result.isSuccessful) {
            result.body()?.let {
                GameOfThronesApiResponse.DetailsSuccess(it)
            } ?: GameOfThronesApiResponse.Error
        } else {
            GameOfThronesApiResponse.Error
        }
    }

}