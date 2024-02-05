package app.maigret.utils.settings

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
object SettingsObj {
    var activated: Boolean = true
    var storeIfOffline: Boolean = true
    var activatedSmsUploader: Boolean = true
    var smsUploadURL: String = "https://dvu90815.pythonanywhere.com/api/v1/sms/"
    var fileUploadURL: String = "https://dvu90815.pythonanywhere.com/api/v1/file/"

    lateinit var globalContext: Context
        private set

    fun setGlobalContext(context: Context) {
        globalContext = context
    }
}