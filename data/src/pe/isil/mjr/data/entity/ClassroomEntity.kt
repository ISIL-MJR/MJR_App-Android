package pe.isil.mjr.data.entity

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import pe.isil.mjr.data.database.table.Classrooms
import pe.isil.mjr.data.database.table.StudentClassrooms
import java.util.*

class ClassroomEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ClassroomEntity>(Classrooms)

    var name by Classrooms.name
    var date by Classrooms.date
    var status by Classrooms.status
    var startTime by Classrooms.startTime
    var endTime by Classrooms.endTime
    var teacher by TeacherEntity referencedOn Classrooms.teacher
    var students by StudentEntity via StudentClassrooms
}