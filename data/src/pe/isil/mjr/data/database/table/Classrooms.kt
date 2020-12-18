package pe.isil.mjr.data.database.table

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.jodatime.datetime

object Classrooms : UUIDTable() {
    val name = varchar("name", length = 50)
    val date = datetime("date")
    val status = integer("status")
    val startTime = datetime("start_time").nullable()
    val endTime = datetime("end_time").nullable()
    val teacher = reference("teacher", Teachers)
}