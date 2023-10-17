package com.example.nbatrivial.data.model

import com.example.nbatrivial.domain.model.Game
import com.google.gson.annotations.SerializedName

class SetNewGameResponse(
    @SerializedName("result") val result: Game
)