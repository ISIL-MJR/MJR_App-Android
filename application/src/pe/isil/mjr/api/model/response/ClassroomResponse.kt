package pe.isil.mjr.api.model.response

import kotlinx.serialization.Serializable

@Serializable
data class Classroom(
    val id: String,
    val name: String,
    val date: Long,
    val status: Int,
    val start_time: Long?,
    val end_time: Long?,
    val teacher: Teacher,
    val students: List<Student>
)

@Serializable
data class ClassroomsResponse(
    override val status: Status,
    override val message: String,
    val classrooms: List<Classroom> = emptyList()
) : Response {

    companion object {

        fun success(message: String, classrooms: List<Classroom>): ClassroomsResponse {
            return ClassroomsResponse(
                Status.SUCCESS,
                message,
                classrooms
            )
        }

        fun unauthorized(message: String): ClassroomsResponse {
            return ClassroomsResponse(
                Status.UNAUTHORIZED,
                message
            )
        }
    }
}

@Serializable
data class ClassroomResponse(
    override val status: Status,
    override val message: String,
    val classroom: Classroom? = null
) : Response {

    companion object {

        fun success(message: String, classroom: Classroom): ClassroomResponse {
            return ClassroomResponse(
                Status.SUCCESS,
                message,
                classroom
            )
        }

        fun notFound(message: String): ClassroomResponse {
            return ClassroomResponse(
                Status.NOT_FOUND,
                message
            )
        }

        fun unauthorized(message: String): ClassroomResponse {
            return ClassroomResponse(
                Status.UNAUTHORIZED,
                message
            )
        }
    }
}