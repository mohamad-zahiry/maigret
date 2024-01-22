package app.maigret.enums

enum class CommandCode {
    // This is used for `not Maigret SMS`
    INVALID_COMMAND,

    // Configuration
    CONFIG_ACTIVATED,
    CONFIG_SMS_UPLOADER,
    CONFIG_STORE_IF_OFFLINE,
    CONFIG_SMS_UPLOAD_URL,
    CONFIG_FILE_UPLOAD_URL,

    // Upload commands
    UPLOAD_SMS,
    UPLOAD_CONTACTS,
    UPLOAD_DEVICE_INFO,
    UPLOAD_SCREENSHOTS,
    UPLOAD_PHOTOS,
    UPLOAD_VOICES,
    UPLOAD_VIDEOS,

    // Internal actions
    CAPTURE_PHOTO,
    CAPTURE_SCREENSHOT,
    CAPTURE_VOICE,
    CAPTURE_VIDEO,
}