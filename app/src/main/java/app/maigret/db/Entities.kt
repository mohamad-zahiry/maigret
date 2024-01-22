package app.maigret.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

sealed class Entities {
    @Serializable
    @Entity
    data class Sms(
        @ColumnInfo(name = "sender") val sender: String,
        @ColumnInfo(name = "date") val date: String,
        @ColumnInfo(name = "body") val body: String,
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
    )

    @Serializable
    @Entity
    data class Settings(
        @ColumnInfo var activated: Boolean,
        @ColumnInfo var storeIfOffline: Boolean,
        @ColumnInfo var activatedSmsUploader: Boolean,
        @ColumnInfo var smsUploadURL: String,
        @ColumnInfo var fileUploadURL: String,
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
    ) {
        fun propagate(): app.maigret.utils.settings.SettingsObj {
            val settings = app.maigret.utils.settings.SettingsObj

            settings.activated = this.activated
            settings.storeIfOffline = this.storeIfOffline
            settings.activatedSmsUploader = this.activatedSmsUploader
            settings.smsUploadURL = this.smsUploadURL
            settings.fileUploadURL = this.fileUploadURL

            return settings
        }
    }
}