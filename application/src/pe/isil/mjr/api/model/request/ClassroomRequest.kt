package pe.isil.mjr.api.model.request

import kotlinx.serialization.Serializable

@Serializable
data class ClassroomRequest(
    val name: String = "",
    val date: Long = 0,
    val status: Int = 0,
    val start_time: Long? = null,
    val end_time: Long? = null,
    val teacher: String = "",
    val students: List<String> = emptyList()
)