package pe.isil.mjr.data.database.table

import org.jetbrains.exposed.dao.id.UUIDTable

object Users : UUIDTable() {
    val username = varchar("username", length = 15)
    val password = text("password")
}