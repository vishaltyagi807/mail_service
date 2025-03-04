package dev.varshit

import kotlinx.serialization.Serializable

@Serializable
data class SendRequest(
    val name: String,
    val from: String? = "varshityagi807@varshit.shop",
    val to: String,
    val phoneNumber: String = "",
    val subject: String = "",
    val doctorName: String = "",
    val date: String = "",
    val note: String? = "",
)
