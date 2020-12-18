package pe.isil.mjr.api.model.response

import kotlinx.serialization.Serializable

@Serializable
data class Teacher(
    val id: String,
    val first_name: String,
    val last_name: String,
    val nationality: String,
    val birth_date: Long,
    val document_type: Int,
    val document_number: String,
    val phone_number: String,
    val email: String,
    val profile_picture: String,
    val status: Int
)

@Serializable
data class TeachersResponse(
    override val status: Status,
    override val message: String,
    val teachers: List<Teacher> = emptyList()
) : Response {

    companion object {

        fun success(message: String, teachers: List<Teacher>): TeachersResponse {
            return TeachersResponse(
                Status.SUCCESS,
                message,
                teachers
            )
        }

        fun unauthorized(message: String): TeachersResponse {
            return TeachersResponse(
                Status.UNAUTHORIZED,
                message
            )
        }
    }
}

@Serializable
data class TeacherResponse(
    override val status: Status,
    override val message: String,
    val teacher: Teacher? = null
) : Response {

    companion object {

        fun success(message: String, teacher: Teacher): TeacherResponse {
            return TeacherResponse(
                Status.SUCCESS,
                message,
                teacher
            )
        }

        fun notFound(message: String): TeacherResponse {
            return TeacherResponse(
                Status.NOT_FOUND,
                message
            )
        }

        fun failed(message: String): TeacherResponse {
            return TeacherResponse(
                Status.FAILED,
                message
            )
        }

        fun unauthorized(message: String): TeacherResponse {
            return TeacherResponse(
                Status.UNAUTHORIZED,
                message
            )
        }
    }
}