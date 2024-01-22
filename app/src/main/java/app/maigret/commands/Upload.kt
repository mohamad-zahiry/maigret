package app.maigret.commands

import app.maigret.db.Entities
import app.maigret.utils.settings.SettingsObj
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable

object Upload {
    private fun upload(url: String, data: @Serializable Any) {
        val client: HttpClient = HttpClient(CIO) {
            install(ContentNegotiation) { json() }
        }

        runBlocking {
            client.post(url) {
                contentType(ContentType.Application.Json)
                setBody(data)
            }
        }
    }

    fun sms(sms: Entities.Sms) {
        upload(SettingsObj.smsUploadURL, sms)
    }
}