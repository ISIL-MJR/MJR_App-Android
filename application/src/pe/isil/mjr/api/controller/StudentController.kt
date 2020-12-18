package pe.isil.mjr.api.controller

import io.ktor.util.*
import pe.isil.mjr.api.exception.BadRequestException
import pe.isil.mjr.api.exception.NotFoundException
import pe.isil.mjr.api.exception.UnauthorizedException
import pe.isil.mjr.api.mapper.fromData
import pe.isil.mjr.api.model.request.StudentRequest
import pe.isil.mjr.api.model.response.ClassroomsResponse
import pe.isil.mjr.api.model.response.StudentResponse
import pe.isil.mjr.api.model.response.StudentsResponse
import pe.isil.mjr.data.dao.StudentDao
import javax.inject.Inject

@KtorExperimentalAPI
class StudentController @Inject constructor(private val dao: StudentDao) {

    fun getAll(): StudentsResponse {
        return try {
            val students = dao.getAll()

            StudentsResponse.success("Operation successful", students.map { it.fromData() })
        } catch (e: UnauthorizedException) {
            StudentsResponse.unauthorized(e.message)
        }
    }

    fun get(id: String): StudentResponse {
        return try {
            val student = dao.get(id)
                ?: throw NotFoundException("Student is not found")

            StudentResponse.success("Operation successful", student.fromData())
        } catch (e: NotFoundException) {
            StudentResponse.notFound(e.message)
        } catch (e: UnauthorizedException) {
            StudentResponse.unauthorized(e.message)
        }
    }

    fun add(request: StudentRequest): StudentResponse {
        return try {
            val firstName = request.first_name
            val lastName = request.last_name
            val birthDate = request.birth_date
            val documentType = request.document_type
            val documentNumber = request.document_number
            val phoneNumber = request.phone_number
            val email = request.email
            val englishLevel = request.english_level
            val profilePicture = request.profile_picture

            if (!dao.isAvailable(documentNumber)) {
                throw BadRequestException("Student is not available")
            }

            val student = dao.add(
                firstName,
                lastName,
                birthDate,
                documentType,
                documentNumber,
                phoneNumber,
                email,
                englishLevel,
                profilePicture
            )

            StudentResponse.success("Operation successful", student.fromData())
        } catch (e: BadRequestException) {
            StudentResponse.failed(e.message)
        } catch (e: UnauthorizedException) {
            StudentResponse.unauthorized(e.message)
        }
    }

    fun update(documentNumber: String, request: StudentRequest): StudentResponse {
        return try {
            val firstName = request.first_name
            val lastName = request.last_name
            val birthDate = request.birth_date
            val phoneNumber = request.phone_number
            val email = request.email
            val englishLevel = request.english_level
            val profilePicture = request.profile_picture

            val student = dao.update(
                documentNumber,
                firstName,
                lastName,
                birthDate,
                phoneNumber,
                email,
                englishLevel,
                profilePicture
            ) ?: throw NotFoundException("Student is not found")

            StudentResponse.success("Operation successful", student.fromData())
        } catch (e: NotFoundException) {
            StudentResponse.notFound(e.message)
        } catch (e: UnauthorizedException) {
            StudentResponse.unauthorized(e.message)
        }
    }

    fun status(documentNumber: String, status: Int): StudentResponse {
        return try {
            val student = dao.status(
                documentNumber,
                status
            ) ?: throw NotFoundException("Student is not found")

            StudentResponse.success("Operation successful", student.fromData())
        } catch (e: NotFoundException) {
            StudentResponse.notFound(e.message)
        } catch (e: UnauthorizedException) {
            StudentResponse.unauthorized(e.message)
        }
    }

    fun classrooms(documentNumber: String): ClassroomsResponse {
        return try {
            val dao = dao.classrooms(documentNumber)

            ClassroomsResponse.success("Operation successful", dao.map { it.fromData() })
        } catch (e: UnauthorizedException) {
            ClassroomsResponse.unauthorized(e.message)
        }
    }
}