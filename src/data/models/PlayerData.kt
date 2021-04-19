package com.plcoding.data.models

data class PlayerData(
    val username: String,
    var isDrawing: Boolean = false,
    var score: Int = 0,
    var rank: Int = 0
)
