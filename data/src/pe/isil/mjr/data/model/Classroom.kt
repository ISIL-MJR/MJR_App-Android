package pe.isil.mjr.data.model

import pe.isil.mjr.data.entity.ClassroomEntity

data class Classroom(
    val id: String,
    val name: String,
    val date: Long,
    val status: Int,
    val startTime: Long?,
    val endTime: Long?,
    val teacher: Teacher,
    val students: List<Student>
) {
    companion object {
        fun fromEntity(entity: ClassroomEntity): Classroom {
            return Classroom(
                entity.id.value.toString(),
                entity.name,
                entity.date.millis,
                entity.status,
                entity.startTime?.millis,
                entity.endTime?.millis,
                Teacher.fromEntity(entity.teacher),
                entity.students.map { Student.fromEntity(it) }
            )
        }
    }
}