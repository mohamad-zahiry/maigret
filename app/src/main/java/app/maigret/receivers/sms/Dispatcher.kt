package app.maigret.receivers.sms

import app.maigret.commands.Config
import app.maigret.commands.InvalidCommand
import app.maigret.commands.Upload
import app.maigret.db.Entities
import app.maigret.enums.CommandCode
import app.maigret.utils.sms.MaigretOrder
import kotlin.reflect.KFunction

// A valid `Maigret SMS` starts with the `SECRET`
private const val SECRET: String = "0000"

class Dispatcher(private val sms: Entities.Sms) {
    /* Find suitable `Action Function` according to `sms`.
     *
     * Check given SMS and if it is a valid `Maigret SMS`, you can
     * get the Action Function by `dispatch` method
     */

    // Check for valid `Maigret SMS`
    val isValid: Boolean = sms.body.length >= SECRET.length + 2 && sms.body.startsWith(SECRET)

    // Extract `Command Code` from `sms`
    private val commandCode: CommandCode = try {
        CommandCode.entries[sms.body.slice(IntRange(SECRET.length, SECRET.length + 1)).toInt()]
    } catch (e: Exception) {
        CommandCode.INVALID_COMMAND
    }

    private val functionOfCommand: Map<CommandCode, KFunction<Any>> = mapOf(
        CommandCode.INVALID_COMMAND to InvalidCommand::doNothing,
        CommandCode.CONFIG_ACTIVATED to Config::setActivated,
        CommandCode.CONFIG_SMS_UPLOADER to Config::setActivatedSmsUploader,
        CommandCode.UPLOAD_CONTACTS to Upload::contact,
    )

    val maigretOrder: MaigretOrder = MaigretOrder(
        commandCode,
        sms.body.slice(IntRange(SECRET.length + 2, sms.body.length - 1)))

    fun dispatch(): KFunction<Any>? = functionOfCommand[commandCode]
}