package pe.isil.mjr.api.controller

import io.ktor.util.*
import pe.isil.mjr.api.exception.NotFoundException
import pe.isil.mjr.api.exception.UnauthorizedException
import pe.isil.mjr.api.mapper.fromData
import pe.isil.mjr.api.model.request.ClassroomRequest
import pe.isil.mjr.api.model.response.ClassroomResponse
import pe.isil.mjr.api.model.response.ClassroomsResponse
import pe.isil.mjr.data.dao.ClassroomDao
import javax.inject.Inject

@KtorExperimentalAPI
class ClassroomController @Inject constructor(private val dao: ClassroomDao) {

    fun getAll(): ClassroomsResponse {
        return try {
            val classrooms = dao.getAll()

            ClassroomsResponse.success("Operation successful", classrooms.map { it.fromData() })
        } catch (e: UnauthorizedException) {
            ClassroomsResponse.unauthorized(e.message)
        }
    }

    fun get(id: String): ClassroomResponse {
        return try {
            val classroom = dao.get(id)
                ?: throw NotFoundException("Classroom is not found")

            ClassroomResponse.success("Operation successful", classroom.fromData())
        } catch (e: NotFoundException) {
            ClassroomResponse.notFound(e.message)
        } catch (e: UnauthorizedException) {
            ClassroomResponse.unauthorized(e.message)
        }
    }

    fun add(request: ClassroomRequest): ClassroomResponse {
        return try {
            val name = request.name
            val date = request.date
            val teacher = request.teacher
            val students = request.students

            val classroom = dao.add(
                name,
                date,
                teacher,
                students
            )

            ClassroomResponse.success("Operation successful", classroom.fromData())
        } catch (e: UnauthorizedException) {
            ClassroomResponse.unauthorized(e.message)
        }
    }

    fun update(id: String, request: ClassroomRequest): ClassroomResponse {
        return try {
            val name = request.name
            val date = request.date
            val teacher = request.teacher
            val students = request.students

            val classroom = dao.update(
                id,
                name,
                date,
                teacher,
                students
            ) ?: throw NotFoundException("Classroom is not found")

            ClassroomResponse.success("Operation successful", classroom.fromData())
        } catch (e: NotFoundException) {
            ClassroomResponse.notFound(e.message)
        } catch (e: UnauthorizedException) {
            ClassroomResponse.unauthorized(e.message)
        }
    }

    fun status(id: String, status: Int): ClassroomResponse {
        return try {
            val classroom = dao.status(
                id,
                status
            ) ?: throw NotFoundException("Classroom is not found")

            ClassroomResponse.success("Operation successful", classroom.fromData())
        } catch (e: NotFoundException) {
            ClassroomResponse.notFound(e.message)
        } catch (e: UnauthorizedException) {
            ClassroomResponse.unauthorized(e.message)
        }
    }

    fun start(id: String, status: Int, startTime: Long?): ClassroomResponse {
        return try {
            val classroom = dao.start(
                id,
                status,
                startTime
            ) ?: throw NotFoundException("Classroom is not found")

            ClassroomResponse.success("Operation successful", classroom.fromData())
        } catch (e: NotFoundException) {
            ClassroomResponse.notFound(e.message)
        } catch (e: UnauthorizedException) {
            ClassroomResponse.unauthorized(e.message)
        }
    }

    fun end(id: String, status: Int, endTime: Long?): ClassroomResponse {
        return try {
            val classroom = dao.end(
                id,
                status,
                endTime
            ) ?: throw NotFoundException("Classroom is not found")

            ClassroomResponse.success("Operation successful", classroom.fromData())
        } catch (e: NotFoundException) {
            ClassroomResponse.notFound(e.message)
        } catch (e: UnauthorizedException) {
            ClassroomResponse.unauthorized(e.message)
        }
    }
}