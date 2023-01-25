package com.chat.plugins

import com.chat.room.RoomController
import com.chat.routes.chatSocket
import com.chat.routes.getAllMessages
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val roomController  by inject<RoomController>()
    routing {
        chatSocket(roomController)
        getAllMessages(roomController)
    }
}
