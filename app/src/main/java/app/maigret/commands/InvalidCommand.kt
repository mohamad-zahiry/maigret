package app.maigret.commands

import app.maigret.db.Entities

object InvalidCommand {
    fun doNothing(sms: Entities.Sms) {}
}