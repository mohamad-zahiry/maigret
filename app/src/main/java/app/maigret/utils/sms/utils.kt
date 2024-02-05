package app.maigret.utils.sms

import android.content.Intent
import android.provider.Telephony
import app.maigret.db.Entities
import app.maigret.enums.CommandCode
import app.maigret.utils.settings.SettingsObj
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

fun intentToSms(intent: Intent): Entities.Sms? {
    val smsList = Telephony.Sms.Intents.getMessagesFromIntent(intent)

    if (smsList.isNotEmpty())
        return Entities.Sms(
            sender = smsList[0].displayOriginatingAddress.toString(),
            date = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US)
                .format(smsList[0].timestampMillis),
            body = smsList[0].displayMessageBody.toString(),
        )

    return null
}

fun uploadSms(sms: Entities.Sms) {
    val client: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) { json() }
    }

    CoroutineScope(Dispatchers.IO).launch {
        client.post(SettingsObj.smsUploadURL) {
            contentType(ContentType.Application.Json)
            setBody(sms)
        }
    }
}

data class MaigretOrder(
    val commandCode: CommandCode,
    val orderText: String,
)