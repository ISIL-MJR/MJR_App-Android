package pe.isil.mjr.api.model.request

import kotlinx.serialization.Serializable

@Serializable
data class TeacherRequest(
    val first_name: String = "",
    val last_name: String = "",
    val nationality: String = "",
    val birth_date: Long = 0,
    val document_type: Int = 0,
    val document_number: String = "",
    val phone_number: String = "",
    val email: String = "",
    val profile_picture: String = "",
    val status: Int = 0
)