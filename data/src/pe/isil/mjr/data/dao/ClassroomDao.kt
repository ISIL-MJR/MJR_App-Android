package pe.isil.mjr.data.dao

import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import pe.isil.mjr.data.entity.ClassroomEntity
import pe.isil.mjr.data.entity.StudentEntity
import pe.isil.mjr.data.entity.TeacherEntity
import pe.isil.mjr.data.model.Classroom
import java.util.*
import javax.inject.Inject

class ClassroomDao @Inject constructor() {

    fun getAll(): List<Classroom> {
        return transaction {
            ClassroomEntity.all()
                .map { Classroom.fromEntity(it) }
        }
    }

    fun get(id: String): Classroom? {
        return transaction {
            ClassroomEntity.findById(UUID.fromString(id))
                ?.let { Classroom.fromEntity(it) }
        }
    }

    fun add(
        name: String,
        date: Long,
        teacher: String,
        students: List<String>
    ): Classroom {
        return transaction {
            ClassroomEntity.new {
                this.name = name
                this.date = DateTime(date)
                this.status = 1
                this.teacher = TeacherEntity[UUID.fromString(teacher)]
                this.students = SizedCollection(students.map { StudentEntity[UUID.fromString(it)] })
            }.let {
                Classroom.fromEntity(it)
            }
        }
    }

    fun update(
        classroom: String,
        name: String,
        date: Long,
        teacher: String,
        students: List<String>
    ): Classroom? {
        return transaction {
            ClassroomEntity.findById(UUID.fromString(classroom))?.apply {
                this.name = name
                this.date = DateTime(date)
                this.teacher = TeacherEntity[UUID.fromString(teacher)]
                this.students = SizedCollection(students.map { StudentEntity[UUID.fromString(it)] })
            }?.let { Classroom.fromEntity(it) }
        }
    }

    fun status(classroom: String, status: Int): Classroom? {
        return transaction {
            ClassroomEntity.findById(UUID.fromString(classroom))?.apply {
                this.status = status
            }?.let { Classroom.fromEntity(it) }
        }
    }

    fun start(classroom: String, status: Int, startTime: Long?): Classroom? {
        return transaction {
            ClassroomEntity.findById(UUID.fromString(classroom))?.apply {
                this.status = status
                this.startTime = DateTime(startTime)
            }?.let { Classroom.fromEntity(it) }
        }
    }

    fun end(classroom: String, status: Int, endTime: Long?): Classroom? {
        return transaction {
            ClassroomEntity.findById(UUID.fromString(classroom))?.apply {
                this.status = status
                this.endTime = DateTime(endTime)
            }?.let { Classroom.fromEntity(it) }
        }
    }
}