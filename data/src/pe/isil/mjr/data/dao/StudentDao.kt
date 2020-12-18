package pe.isil.mjr.data.dao

import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import pe.isil.mjr.data.database.table.Students
import pe.isil.mjr.data.entity.ClassroomEntity
import pe.isil.mjr.data.entity.StudentEntity
import pe.isil.mjr.data.model.Classroom
import pe.isil.mjr.data.model.Student
import java.util.*
import javax.inject.Inject

class StudentDao @Inject constructor() {

    fun getAll(): List<Student> {
        return transaction {
            StudentEntity.all()
                .map { Student.fromEntity(it) }
        }
    }

    fun get(id: String): Student? {
        return transaction {
            StudentEntity.findById(UUID.fromString(id))
                ?.let { Student.fromEntity(it) }
        }
    }

    fun add(
        firstName: String,
        lastName: String,
        birthDate: Long,
        documentType: Int,
        documentNumber: String,
        phoneNumber: String,
        email: String,
        englishLevel: String,
        profilePicture: String
    ): Student {
        return transaction {
            StudentEntity.new {
                this.firstName = firstName
                this.lastName = lastName
                this.birthDate = DateTime(birthDate)
                this.documentType = documentType
                this.documentNumber = documentNumber
                this.phoneNumber = phoneNumber
                this.email = email
                this.englishLevel = englishLevel
                this.profilePicture = profilePicture
                this.status = 1
            }.let { Student.fromEntity(it) }
        }
    }

    fun update(
        documentNumber: String,
        firstName: String,
        lastName: String,
        birthDate: Long,
        phoneNumber: String,
        email: String,
        englishLevel: String,
        profilePicture: String
    ): Student? {
        return transaction {
            StudentEntity.find {
                Students.documentNumber eq documentNumber
            }.firstOrNull()?.apply {
                this.firstName = firstName
                this.lastName = lastName
                this.birthDate = DateTime(birthDate)
                this.phoneNumber = phoneNumber
                this.email = email
                this.englishLevel = englishLevel
                this.profilePicture = profilePicture
            }?.let { Student.fromEntity(it) }
        }
    }

    fun status(documentNumber: String, status: Int): Student? {
        return transaction {
            StudentEntity.find {
                Students.documentNumber eq documentNumber
            }.firstOrNull()?.apply {
                this.status = status
            }?.let { Student.fromEntity(it) }
        }
    }

    fun classrooms(documentNumber: String): List<Classroom> {
        return transaction {
            ClassroomEntity.all()
                .filter {
                    it.students.any { student ->
                        student.documentNumber.contains(documentNumber)
                    }
                }.map { Classroom.fromEntity(it) }
        }
    }

    fun isAvailable(documentNumber: String): Boolean {
        return transaction {
            StudentEntity.find {
                Students.documentNumber eq documentNumber
            }.firstOrNull() == null
        }
    }
}