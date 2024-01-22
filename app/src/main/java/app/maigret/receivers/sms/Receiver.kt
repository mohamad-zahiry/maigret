package app.maigret.receivers.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import app.maigret.commands.Upload
import app.maigret.db.Entities
import app.maigret.utils.settings.SettingsObj
import app.maigret.utils.sms.intentToSms

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val sms: Entities.Sms = intentToSms(intent)
                ?: return

            val dispatcher: Dispatcher = Dispatcher(sms)
            println(dispatcher.isValid)
            dispatcher.commandCode()
            if (dispatcher.isValid)
                dispatcher.dispatch()?.call(sms)

            // Do nothing if Maigret is not activated
            if (SettingsObj.activated.not())
                return

            // Send newly received sms to server
            if (SettingsObj.activatedSmsUploader)
                Upload.sms(sms)
        }
    }
}