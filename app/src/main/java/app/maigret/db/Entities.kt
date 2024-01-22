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
    ) {
        fun serialize() = mapOf(
            "sender" to sender,
            "date" to date,
            "body" to body,
        )
    }
}