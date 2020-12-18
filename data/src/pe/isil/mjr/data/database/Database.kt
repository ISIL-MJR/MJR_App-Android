package pe.isil.mjr.data.database

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import pe.isil.mjr.data.database.table.*

fun initDatabase(
    host: String,
    port: String,
    name: String,
    user: String,
    password: String
) {
    val tables = arrayOf(Users, Teachers, Students, Classrooms, StudentClassrooms)

    Database.connect(
        url = "jdbc:postgresql://$host:$port/$name",
        driver = "org.postgresql.Driver",
        user = user,
        password = password
    )

    transaction {
        SchemaUtils.createMissingTablesAndColumns(*tables)
    }
}