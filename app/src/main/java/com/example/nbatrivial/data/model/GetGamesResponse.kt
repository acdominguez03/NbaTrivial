package com.example.nbatrivial.data.model

import com.example.nbatrivial.domain.model.Game
import com.google.gson.annotations.SerializedName

data class GetGamesResponse(
    @SerializedName("games") val games: List<Game>
)