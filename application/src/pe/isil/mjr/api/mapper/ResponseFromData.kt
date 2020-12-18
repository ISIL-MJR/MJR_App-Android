package pe.isil.mjr.api.mapper

import pe.isil.mjr.api.model.response.Classroom as ClassroomResponse
import pe.isil.mjr.api.model.response.Student as StudentResponse
import pe.isil.mjr.api.model.response.Teacher as TeacherResponse
import pe.isil.mjr.data.model.Classroom as ClassroomData
import pe.isil.mjr.data.model.Student as StudentData
import pe.isil.mjr.data.model.Teacher as TeacherData

fun TeacherData.fromData(): TeacherResponse {
    return TeacherResponse(
        id = id,
        first_name = firstName,
        last_name = lastName,
        nationality = nationality,
        birth_date = birthDate,
        document_type = documentType,
        document_number = documentNumber,
        phone_number = phoneNumber,
        email = email,
        profile_picture = profilePicture,
        status = status
    )
}

fun StudentData.fromData(): StudentResponse {
    return StudentResponse(
        id = id,
        first_name = firstName,
        last_name = lastName,
        birth_date = birthDate,
        document_type = documentType,
        document_number = documentNumber,
        phone_number = phoneNumber,
        email = email,
        english_level = englishLevel,
        profile_picture = profilePicture,
        status = status
    )
}

fun ClassroomData.fromData(): ClassroomResponse {
    return ClassroomResponse(
        id = id,
        name = name,
        date = date,
        status = status,
        start_time = startTime,
        end_time = endTime,
        teacher = teacher.fromData(),
        students = students.map { it.fromData() }
    )
}