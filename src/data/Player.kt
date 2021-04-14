package com.plcoding.data

import io.ktor.http.cio.websocket.*

data class Player(
    val username: String,
    var socket: WebSocketSession,
    val clientId: String,
    var isDrawing: Boolean = false,
    var score: Int = 0,
    var rank: Int = 0
)
