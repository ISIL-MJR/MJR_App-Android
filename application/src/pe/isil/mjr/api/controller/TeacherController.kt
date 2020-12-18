package pe.isil.mjr.api.controller

import io.ktor.util.*
import pe.isil.mjr.api.exception.BadRequestException
import pe.isil.mjr.api.exception.NotFoundException
import pe.isil.mjr.api.exception.UnauthorizedException
import pe.isil.mjr.api.mapper.fromData
import pe.isil.mjr.api.model.request.TeacherRequest
import pe.isil.mjr.api.model.response.ClassroomsResponse
import pe.isil.mjr.api.model.response.TeacherResponse
import pe.isil.mjr.api.model.response.TeachersResponse
import pe.isil.mjr.data.dao.TeacherDao
import javax.inject.Inject

@KtorExperimentalAPI
class TeacherController @Inject constructor(private val dao: TeacherDao) {

    fun getAll(): TeachersResponse {
        return try {
            val teachers = dao.getAll()

            TeachersResponse.success("Operation successful", teachers.map { it.fromData() })
        } catch (e: UnauthorizedException) {
            TeachersResponse.unauthorized(e.message)
        }
    }

    fun get(documentNumber: String): TeacherResponse {
        return try {
            val teacher = dao.get(documentNumber)
                ?: throw NotFoundException("Teacher is not found")

            TeacherResponse.success("Operation successful", teacher.fromData())
        } catch (e: NotFoundException) {
            TeacherResponse.notFound(e.message)
        } catch (e: UnauthorizedException) {
            TeacherResponse.unauthorized(e.message)
        }
    }

    fun add(request: TeacherRequest): TeacherResponse {
        return try {
            val firstName = request.first_name
            val lastName = request.last_name
            val nationality = request.nationality
            val birthDate = request.birth_date
            val documentType = request.document_type
            val documentNumber = request.document_number
            val phoneNumber = request.phone_number
            val email = request.email
            val profilePicture = request.profile_picture

            if (!dao.isAvailable(documentNumber)) {
                throw BadRequestException("Teacher is not available")
            }

            val teacher = dao.add(
                firstName,
                lastName,
                nationality,
                birthDate,
                documentType,
                documentNumber,
                phoneNumber,
                email,
                profilePicture
            )

            TeacherResponse.success("Operation successful", teacher.fromData())
        } catch (e: BadRequestException) {
            TeacherResponse.failed(e.message)
        } catch (e: UnauthorizedException) {
            TeacherResponse.unauthorized(e.message)
        }
    }

    fun update(documentNumber: String, request: TeacherRequest): TeacherResponse {
        return try {
            val firstName = request.first_name
            val lastName = request.last_name
            val nationality = request.nationality
            val birthDate = request.birth_date
            val phoneNumber = request.phone_number
            val email = request.email
            val profilePicture = request.profile_picture

            val teacher = dao.update(
                documentNumber,
                firstName,
                lastName,
                nationality,
                birthDate,
                phoneNumber,
                email,
                profilePicture
            ) ?: throw NotFoundException("Teacher is not found")

            TeacherResponse.success("Operation successful", teacher.fromData())
        } catch (e: NotFoundException) {
            TeacherResponse.notFound(e.message)
        } catch (e: UnauthorizedException) {
            TeacherResponse.unauthorized(e.message)
        }
    }

    fun status(documentNumber: String, status: Int): TeacherResponse {
        return try {
            val teacher = dao.status(
                documentNumber,
                status
            ) ?: throw NotFoundException("Teacher is not found")

            TeacherResponse.success("Operation successful", teacher.fromData())
        } catch (e: NotFoundException) {
            TeacherResponse.notFound(e.message)
        } catch (e: UnauthorizedException) {
            TeacherResponse.unauthorized(e.message)
        }
    }

    fun classrooms(documentNumber: String): ClassroomsResponse {
        return try {
            val classrooms = dao.classrooms(documentNumber)

            ClassroomsResponse.success("Operation successful", classrooms.map { it.fromData() })
        } catch (e: UnauthorizedException) {
            ClassroomsResponse.unauthorized(e.message)
        }
    }
}