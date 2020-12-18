package pe.isil.mjr.data.entity

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import pe.isil.mjr.data.database.table.Teachers
import java.util.*

class TeacherEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<TeacherEntity>(Teachers)

    var firstName by Teachers.firstName
    var lastName by Teachers.lastName
    var nationality by Teachers.nationality
    var birthDate by Teachers.birthDate
    var documentType by Teachers.documentType
    var documentNumber by Teachers.documentNumber
    var phoneNumber by Teachers.phoneNumber
    var email by Teachers.email
    var profilePicture by Teachers.profilePicture
    var status by Teachers.status
}