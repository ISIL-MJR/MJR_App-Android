package pe.isil.mjr.data.model

import pe.isil.mjr.data.entity.UserEntity

data class User(
    val id: String,
    val username: String,
    val password: String
) {
    companion object {
        fun fromEntity(entity: UserEntity): User {
            return User(
                entity.id.value.toString(),
                entity.username,
                entity.password
            )
        }
    }
}