package edu.nku.classapp.data

import edu.nku.classapp.model.GameOfThronesCharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface GameOfThronesApi {
    @GET("api/v2/Characters")
    suspend fun getCharacters(): Response<List<GameOfThronesCharacterResponse.Character>>

    @GET("api/v2/Characters/{id}")
    suspend fun getCharacterDetail(@Path("id") id: Int): Response<GameOfThronesCharacterResponse.Character>
}