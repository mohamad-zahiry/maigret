package app.maigret.utils.contact

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import kotlinx.serialization.Serializable

@Serializable
data class Contact(
    val name: String,
    val phone: String,
)

fun readContacts(context: Context): List<Contact>? {
    val contactList: MutableList<Contact> = mutableListOf()
    val contactTableColumns = arrayOf(
        ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
    )

    // Access contact table or return empty list
    val cursor: Cursor = context.contentResolver?.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        contactTableColumns,
        null,
        null,
        null,
    ) ?: return null

    // Move  Cursor to first contact
    cursor.moveToFirst()

    // read all contacts data
    do {
        contactList.add(Contact(cursor.getString(0), cursor.getString(1)))
    } while (cursor.moveToNext())

    // The cursor must be closed to free up the resource
    cursor.close()

    return contactList
}