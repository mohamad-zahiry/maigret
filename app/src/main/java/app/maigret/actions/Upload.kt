package app.maigret.actions

import app.maigret.models.Sms
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking

object Upload {
    fun sms(smsData: Sms) {
        val url = "http://dvu90815.pythonanywhere.com/api/v1/sms/"
        val client: HttpClient = HttpClient(CIO) {
            install(ContentNegotiation) { json() }
        }

        runBlocking {
            client.post(url) {
                contentType(ContentType.Application.Json)
                setBody(smsData)
            }
        }
    }
}