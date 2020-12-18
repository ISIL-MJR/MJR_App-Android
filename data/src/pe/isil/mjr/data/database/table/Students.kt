package pe.isil.mjr.data.database.table

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.jodatime.datetime

object Students : UUIDTable() {
    val firstName = varchar("first_name", length = 50)
    val lastName = varchar("last_name", length = 50)
    val birthDate = datetime("birth_date")
    val documentType = integer("document_type")
    val documentNumber = varchar("document_number", length = 12)
    val phoneNumber = varchar("phone_number", length = 12)
    val email = varchar("email", length = 50)
    val englishLevel = varchar("english_level", length = 50)
    val profilePicture = text("profile_picture")
    val status = integer("status")
}