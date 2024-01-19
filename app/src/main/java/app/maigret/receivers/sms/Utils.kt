package app.maigret.receivers.sms

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import app.maigret.models.Contact

class Utils(private val context: Context) {
    fun readContacts(): List<Contact>? {
        val contactList: MutableList<Contact> = mutableListOf()
        val contactTableColumns = arrayOf(
            ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
        )

        // access contact table or return empty list
        val cursor: Cursor = this.context.contentResolver?.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            contactTableColumns,
            null,
            null,
            null,
        ) ?: return null

        cursor.moveToFirst()

        // read all contacts data
        do contactList.add(Contact(cursor.getString(0), cursor.getString(1)))
        while (cursor.moveToNext())

        // cursor must close to free up the resource
        cursor.close()

        return contactList
    }
}