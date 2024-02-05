package app.maigret.receivers.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import app.maigret.db.Entities
import app.maigret.utils.settings.SettingsObj
import app.maigret.utils.sms.intentToSms
import app.maigret.utils.sms.uploadSms

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val sms: Entities.Sms = intentToSms(intent)
                ?: return

            val dispatcher: Dispatcher = Dispatcher(sms)
            if (dispatcher.isValid)
                dispatcher.dispatch()?.call(dispatcher.maigretOrder)

            // Do nothing if Maigret is not activated
            if (SettingsObj.activated.not())
                return

            // Send newly received sms to server
            if (SettingsObj.activatedSmsUploader)
                uploadSms(sms)
        }
    }
}