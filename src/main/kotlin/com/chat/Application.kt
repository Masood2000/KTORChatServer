package com.chat


import com.chat.di.mainModule
import io.ktor.server.application.*
import com.chat.plugins.*
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.loadKoinModules
import org.koin.ktor.plugin.Koin


fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module()
{
    install(Koin){
        modules(mainModule)
    }
    configureSockets()
    configureSerialization()
    configureMonitoring()
    configureSecurity()
    configureRouting()
}
