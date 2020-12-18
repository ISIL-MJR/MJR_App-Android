package pe.isil.mjr.data.dao

import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import pe.isil.mjr.data.database.table.Users
import pe.isil.mjr.data.entity.UserEntity
import pe.isil.mjr.data.model.User
import java.util.*
import javax.inject.Inject

class UserDao @Inject constructor() {

    fun add(username: String, password: String): User {
        return transaction {
            UserEntity.new {
                this.username = username
                this.password = password
            }.let { User.fromEntity(it) }
        }
    }

    fun get(username: String, password: String): User? {
        return transaction {
            UserEntity.find {
                (Users.username eq username) and (Users.password eq password)
            }.firstOrNull()?.let { User.fromEntity(it) }
        }
    }

    fun isAvailable(username: String): Boolean {
        return transaction {
            UserEntity.find {
                Users.username eq username
            }.firstOrNull() == null
        }
    }

    fun isExists(id: String): Boolean {
        return transaction {
            UserEntity.find {
                Users.id eq UUID.fromString(id)
            }.firstOrNull() != null
        }
    }
}