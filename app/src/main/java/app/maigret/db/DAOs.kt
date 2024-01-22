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
}