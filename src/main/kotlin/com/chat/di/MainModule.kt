package com.chat.di

import com.chat.data.model.MessageDataSource
import com.chat.data.model.MessageDataSourceImpl
import com.chat.room.RoomController
import org.koin.dsl.module
import org.litote.kmongo.KMongo
import org.litote.kmongo.coroutine.coroutine

val mainModule  = module{
    single{
        org.litote.kmongo.reactivestreams.KMongo.createClient()
            .coroutine
            .getDatabase("message_db")
    }
    single<MessageDataSource>{
        MessageDataSourceImpl(get())
    }
    single {
        RoomController(get())
    }
}