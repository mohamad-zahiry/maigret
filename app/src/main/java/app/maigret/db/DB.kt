package app.maigret.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.maigret.utils.settings.SettingsObj
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val DB_NAME = "maigret.db"

@Database(
    entities = [Entities.Sms::class, Entities.Settings::class],
    version = 1,
)
abstract class DB :RoomDatabase() {
    abstract fun smsDao(): DAOs.Sms

    abstract fun settingsDao(): DAOs.Settings
}

object DatabaseManager {
    lateinit var db: DB
    fun initDB() {
        this.db = Room.databaseBuilder(
            SettingsObj.globalContext, DB::class.java, DB_NAME)
            .enableMultiInstanceInvalidation()
            .build()

        CoroutineScope(Dispatchers.IO).launch {
            initDefaultSettings()
        }
    }

    private fun initDefaultSettings() {
        // Set default settings for Maigret if it is not set
        val settings: Entities.Settings? = db.settingsDao().getLast()

        if (settings == null) {
            val defaultSettings = Entities.Settings(
                activated = SettingsObj.activated,
                activatedSmsUploader = SettingsObj.activatedSmsUploader,
                fileUploadURL = SettingsObj.fileUploadURL,
                smsUploadURL = SettingsObj.smsUploadURL,
                storeIfOffline = SettingsObj.storeIfOffline)

            this.db.settingsDao().bulkInsert(defaultSettings)
        }
    }
}