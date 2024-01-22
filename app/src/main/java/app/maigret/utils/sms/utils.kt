package app.maigret.utils.sms

import android.content.Intent
import android.provider.Telephony
import app.maigret.db.Entities
import app.maigret.enums.CommandCode
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

data class MaigretOrder(
    val commandCode: CommandCode,
    val orderText: String,
)