package com.plcoding.routes

import com.google.gson.JsonParser
import com.plcoding.data.Room
import com.plcoding.data.models.BaseModel
import com.plcoding.data.models.ChatMessage
import com.plcoding.data.models.DrawData
import com.plcoding.gson
import com.plcoding.other.Constants.TYPE_CHAT_MESSAGE
import com.plcoding.other.Constants.TYPE_DRAW_DATA
import com.plcoding.server
import com.plcoding.session.DrawingSession
import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach

fun Route.gameWebSocketRoute() {
    route("/ws/draw") {
        standardWebSocket { socket, clientId, message, payload ->
            when(payload) {
                is DrawData -> {
                    val room = server.rooms[payload.roomName] ?: return@standardWebSocket
                    if(room.phase == Room.Phase.GAME_RUNNING) {
                        room.broadcastToAllExcept(message, clientId)
                    }
                }
                is ChatMessage -> {

                }
            }
        }
    }
}

fun Route.standardWebSocket(
    handleFrame: suspend (
        socket: DefaultWebSocketServerSession,
        clientId: String,
        message: String,
        payload: BaseModel
    ) -> Unit
) {
    webSocket {
        val session = call.sessions.get<DrawingSession>()
        if(session == null) {
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No session."))
            return@webSocket
        }
        try {
            incoming.consumeEach { frame ->
                if(frame is Frame.Text) {
                    val message = frame.readText()
                    val jsonObject = JsonParser.parseString(message).asJsonObject
                    val type = when(jsonObject.get("type").asString) {
                        TYPE_CHAT_MESSAGE -> ChatMessage::class.java
                        TYPE_DRAW_DATA -> DrawData::class.java
                        else -> BaseModel::class.java
                    }
                    val payload = gson.fromJson(message, type)
                    handleFrame(this, session.clientId, message, payload)
                }
            }
        } catch(e: Exception) {
            e.printStackTrace()
        }
        finally {
            // Handle disconnects
        }
    }
}