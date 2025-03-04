package dev.varshit

import com.resend.Resend
import com.resend.services.emails.model.CreateEmailOptions
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import java.io.File

object MailTemplate {
    fun getTemplate(data: SendRequest): String {
        val file = File("template.html")
        var fileContent =
            file.readText().replace("[Name]", data.name)
                .replace("[Date]", data.date)
                .replace("[Address]", "MIET, Meerut")
                .replace("[Your Name]", data.doctorName)
                .replace("[Doctor]", data.doctorName)
                .replace("[Phone]", data.phoneNumber)
                .replace("[Contact Information]", "example@gmail.com")
        fileContent = if (!data.note.isNullOrEmpty()) {
            fileContent
                .replace("[<p>&bull; Time: [Time]</p>]", "<p>&bull; Note: ${data.note}</p>")
        } else {
            fileContent
                .replace("[<p>&bull; Time: [Time]</p>]", "")
        }
        return fileContent
    }
}

@Serializable
data class Res(val status: String)

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respond(Res("working!"))
        }
        post("/send") {
            try {
                runBlocking {
                    val request = call.receive<SendRequest>()
                    val data = MailTemplate.getTemplate(request)
                    val resend = Resend("re_Bo2gj2qS_2TS3HWKF1CZJxHduexrnmwmM")
                    val params = CreateEmailOptions.builder()
                        .from(request.from)
                        .to(request.to)
                        .subject(request.subject)
                        .html(data)
                        .build()
                    resend.emails().send(params)
                    call.respond(HttpStatusCode.OK, request)
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, e.message ?: "Something went wrong")
                println("Error : " + e.message)
            }
        }
    }
}
