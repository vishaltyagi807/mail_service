package dev.varshit

import io.ktor.http.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(io.ktor.server.plugins.cors.routing.CORS) {
        anyHost()
        allowCredentials = true
        allowSameOrigin = true
        anyMethod()
        allowMethod(HttpMethod.Post)
        allowHeader(HttpHeaders.ContentType)
        allowHost("localhost:5500")
    }
    configureSerialization()
    configureRouting()

}
