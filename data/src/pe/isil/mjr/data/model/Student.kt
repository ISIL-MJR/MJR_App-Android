package pe.isil.mjr.data.model

import pe.isil.mjr.data.entity.StudentEntity

data class Student(
    val id: String,
    val firstName: String,
    val lastName: String,
    val birthDate: Long,
    val documentType: Int,
    val documentNumber: String,
    val phoneNumber: String,
    val email: String,
    val englishLevel: String,
    val profilePicture: String,
    val status: Int
) {
    companion object {
        fun fromEntity(entity: StudentEntity): Student {
            return Student(
                entity.id.value.toString(),
                entity.firstName,
                entity.lastName,
                entity.birthDate.millis,
                entity.documentType,
                entity.documentNumber,
                entity.phoneNumber,
                entity.email,
                entity.englishLevel,
                entity.profilePicture,
                entity.status
            )
        }
    }
}