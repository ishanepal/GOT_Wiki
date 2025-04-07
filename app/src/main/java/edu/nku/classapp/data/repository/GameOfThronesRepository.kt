package edu.nku.classapp.data.repository

import edu.nku.classapp.data.model.GameOfThronesApiResponse

interface GameOfThronesRepository {
    suspend fun getCharacters(): GameOfThronesApiResponse
    suspend fun getCharacterDetail(workId: Int): GameOfThronesApiResponse
}