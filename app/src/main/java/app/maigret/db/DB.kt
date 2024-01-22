package app.maigret.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private const val DB_NAME = "maigret.db"

@Database(
    entities = [Entities.Sms::class],
    version = 1,
)
abstract class DB : RoomDatabase() {
    abstract fun smsDao(): DAOs.Sms
}

fun getDB(context: Context): DB {
    val db: DB = Room.databaseBuilder(
        context,
        DB::class.java,
        DB_NAME
    ).enableMultiInstanceInvalidation().build()

    return db
}

