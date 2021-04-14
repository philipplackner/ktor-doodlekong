package com.plcoding.data.models

import com.plcoding.other.Constants.TYPE_JOIN_ROOM_HANDSHAKE

data class JoinRoomHandshake(
    val username: String,
    val roomName: String,
    val clientId: String
): BaseModel(TYPE_JOIN_ROOM_HANDSHAKE)
