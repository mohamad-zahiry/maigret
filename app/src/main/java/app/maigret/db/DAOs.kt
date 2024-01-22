package app.maigret.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

sealed class DAOs {
    @Dao
    interface Sms {
        @Query("SELECT * FROM sms")
        fun getAll(): List<Entities.Sms>

        @Insert
        fun insertAll(vararg users: Entities.Sms)

        @Delete
        fun delete(sms: Entities.Sms)
    }

    @Dao
    interface Settings {
        @Insert
        fun bulkInsert(varargs: Entities.Settings)

        @Query("SELECT * FROM settings ORDER BY id DESC LIMIT 1")
        fun getLast(): Entities.Settings?

        @Query("UPDATE settings SET activated=1")
        fun activate()

        @Query("UPDATE settings SET activated=0")
        fun deactivate()
    }
}