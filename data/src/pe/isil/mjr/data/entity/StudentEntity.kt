package pe.isil.mjr.data.entity

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import pe.isil.mjr.data.database.table.Students
import java.util.*

class StudentEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<StudentEntity>(Students)

    var firstName by Students.firstName
    var lastName by Students.lastName
    var birthDate by Students.birthDate
    var documentType by Students.documentType
    var documentNumber by Students.documentNumber
    var phoneNumber by Students.phoneNumber
    var email by Students.email
    var englishLevel by Students.englishLevel
    var profilePicture by Students.profilePicture
    var status by Students.status
}