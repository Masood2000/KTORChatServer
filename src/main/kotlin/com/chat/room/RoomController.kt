package com.chat.room

import com.chat.data.model.Message
import com.chat.data.model.MessageDataSource
import io.ktor.http.ContentType.Application.Json
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import org.litote.kmongo.text
import java.util.concurrent.ConcurrentHashMap

class RoomController(
    private val messageDataSource: MessageDataSource
) {
    private val members = ConcurrentHashMap<String, Member>()
    fun onJoin(
        username: String,
        sessionId: String,
        socket: WebSocketSession
    ) {
        if (members.containsKey(username)) {
            throw MemberAlreadyExistException()
        }
        members[username] = Member(
            username = username,
            sessionId = sessionId,
            socket = socket
        )
    }

    suspend fun sentMessage(
        senderUsername: String,
        message: String
    ) {
        members.values.forEach { member ->
            val messageEntity = Message(
                text = message,
                username = senderUsername,
                timestamp = System.currentTimeMillis()
            )
            messageDataSource.insertMessage(messageEntity)

            val parsedMessage = kotlinx.serialization.json.Json.encodeToString(messageEntity)
            member.socket.send(Frame.Text(parsedMessage))


        }

    }

suspend fun getAllMessages() : List<Message>{
    return messageDataSource.getAllMessages()
}
    suspend fun tryDisconnect(username: String){
        members[username]?.socket?.close()
        if(members.containsKey(username)){
            members.remove(username)
        }

    }
}