package pe.isil.mjr.data.entity

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import pe.isil.mjr.data.database.table.Users
import java.util.*

class UserEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserEntity>(Users)

    var username by Users.username
    var password by Users.password
}