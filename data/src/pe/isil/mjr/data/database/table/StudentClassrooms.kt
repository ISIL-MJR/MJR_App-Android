package pe.isil.mjr.data.database.table

import org.jetbrains.exposed.dao.id.UUIDTable

object StudentClassrooms : UUIDTable(name = "student_classrooms") {
    val student = reference("student", Students)
    val classroom = reference("classroom", Classrooms)
}