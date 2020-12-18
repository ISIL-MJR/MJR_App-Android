package pe.isil.mjr.data.dao

import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import pe.isil.mjr.data.database.table.Teachers
import pe.isil.mjr.data.entity.ClassroomEntity
import pe.isil.mjr.data.entity.TeacherEntity
import pe.isil.mjr.data.model.Classroom
import pe.isil.mjr.data.model.Teacher
import javax.inject.Inject

class TeacherDao @Inject constructor() {

    fun getAll(): List<Teacher> {
        return transaction {
            TeacherEntity.all()
                .map { Teacher.fromEntity(it) }
        }
    }

    fun get(documentNumber: String): Teacher? {
        return transaction {
            TeacherEntity.find {
                Teachers.documentNumber eq documentNumber
            }.firstOrNull()?.let { Teacher.fromEntity(it) }
        }
    }

    fun add(
        firstName: String,
        lastName: String,
        nationality: String,
        birthDate: Long,
        documentType: Int,
        documentNumber: String,
        phoneNumber: String,
        email: String,
        profilePicture: String
    ): Teacher {
        return transaction {
            TeacherEntity.new {
                this.firstName = firstName
                this.lastName = lastName
                this.nationality = nationality
                this.birthDate = DateTime(birthDate)
                this.documentType = documentType
                this.documentNumber = documentNumber
                this.phoneNumber = phoneNumber
                this.email = email
                this.profilePicture = profilePicture
                this.status = 1
            }.let { Teacher.fromEntity(it) }
        }
    }

    fun update(
        documentNumber: String,
        firstName: String,
        lastName: String,
        nationality: String,
        birthDate: Long,
        phoneNumber: String,
        email: String,
        profilePicture: String
    ): Teacher? {
        return transaction {
            TeacherEntity.find {
                Teachers.documentNumber eq documentNumber
            }.firstOrNull()?.apply {
                this.firstName = firstName
                this.lastName = lastName
                this.nationality = nationality
                this.birthDate = DateTime(birthDate)
                this.phoneNumber = phoneNumber
                this.email = email
                this.profilePicture = profilePicture
            }?.let { Teacher.fromEntity(it) }
        }
    }

    fun status(documentNumber: String, status: Int): Teacher? {
        return transaction {
            TeacherEntity.find {
                Teachers.documentNumber eq documentNumber
            }.firstOrNull()?.apply {
                this.status = status
            }?.let { Teacher.fromEntity(it) }
        }
    }

    fun classrooms(documentNumber: String): List<Classroom> {
        return transaction {
            ClassroomEntity.all()
                .filter { it.teacher.documentNumber == documentNumber }
                .map { Classroom.fromEntity(it) }
        }
    }

    fun isAvailable(documentNumber: String): Boolean {
        return transaction {
            TeacherEntity.find {
                Teachers.documentNumber eq documentNumber
            }.firstOrNull() == null
        }
    }
}