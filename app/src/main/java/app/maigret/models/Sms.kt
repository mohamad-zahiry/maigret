package app.maigret.models

import kotlinx.serialization.Serializable

@Serializable
data class Sms(
    val sender: String,
    val date: String,
    val body: String,
)