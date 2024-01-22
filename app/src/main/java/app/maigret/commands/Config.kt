package app.maigret.commands

import app.maigret.db.DatabaseManager
import app.maigret.utils.sms.MaigretOrder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Config {
    fun setActivated(maigretOrder: MaigretOrder) {
        // Activate/Deactivate Maigret
        CoroutineScope(Dispatchers.IO).launch {
            when (maigretOrder.orderText.slice(0..0)) {
                "1" -> DatabaseManager.db.settingsDao().activate()
                "0" -> DatabaseManager.db.settingsDao().deactivate()
            }

            DatabaseManager.db.settingsDao().getLast()?.propagate()
        }
    }
}