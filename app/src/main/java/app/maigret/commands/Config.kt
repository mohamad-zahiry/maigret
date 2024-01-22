package app.maigret.commands

import app.maigret.db.DatabaseManager
import app.maigret.utils.sms.MaigretOrder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Config {
    private val db = DatabaseManager.db
    fun setActivated(maigretOrder: MaigretOrder) {
        // Activate/Deactivate Maigret
        CoroutineScope(Dispatchers.IO).launch {
            when (maigretOrder.orderText.slice(0..0)) {
                "1" -> db.settingsDao().activate()
                "0" -> db.settingsDao().deactivate()
            }

            db.settingsDao().getLast()?.propagate()
        }
    }

}