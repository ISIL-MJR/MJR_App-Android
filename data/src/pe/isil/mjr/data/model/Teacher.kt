package pe.isil.mjr.data.model

import pe.isil.mjr.data.entity.TeacherEntity

data class Teacher(
    val id: String,
    val firstName: String,
    val lastName: String,
    val nationality: String,
    val birthDate: Long,
    val documentType: Int,
    val documentNumber: String,
    val phoneNumber: String,
    val email: String,
    val profilePicture: String,
    val status: Int
) {
    companion object {
        fun fromEntity(entity: TeacherEntity): Teacher {
            return Teacher(
                entity.id.value.toString(),
                entity.firstName,
                entity.lastName,
                entity.nationality,
                entity.birthDate.millis,
                entity.documentType,
                entity.documentNumber,
                entity.phoneNumber,
                entity.email,
                entity.profilePicture,
                entity.status
            )
        }
    }
}