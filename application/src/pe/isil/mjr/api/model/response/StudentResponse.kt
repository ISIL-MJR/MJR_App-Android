package pe.isil.mjr.api.model.response

import kotlinx.serialization.Serializable

@Serializable
data class Student(
    val id: String,
    val first_name: String,
    val last_name: String,
    val birth_date: Long,
    val document_type: Int,
    val document_number: String,
    val phone_number: String,
    val email: String,
    val english_level: String,
    val profile_picture: String,
    val status: Int
)

@Serializable
data class StudentsResponse(
    override val status: Status,
    override val message: String,
    val students: List<Student> = emptyList()
) : Response {

    companion object {

        fun success(message: String, students: List<Student>): StudentsResponse {
            return StudentsResponse(
                Status.SUCCESS,
                message,
                students
            )
        }

        fun unauthorized(message: String): StudentsResponse {
            return StudentsResponse(
                Status.UNAUTHORIZED,
                message
            )
        }
    }
}

@Serializable
data class StudentResponse(
    override val status: Status,
    override val message: String,
    val student: Student? = null
) : Response {

    companion object {

        fun success(message: String, student: Student): StudentResponse {
            return StudentResponse(
                Status.SUCCESS,
                message,
                student
            )
        }

        fun notFound(message: String): StudentResponse {
            return StudentResponse(
                Status.NOT_FOUND,
                message
            )
        }

        fun failed(message: String): StudentResponse {
            return StudentResponse(
                Status.FAILED,
                message
            )
        }

        fun unauthorized(message: String): StudentResponse {
            return StudentResponse(
                Status.UNAUTHORIZED,
                message
            )
        }
    }
}