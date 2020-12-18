package pe.isil.mjr.api.di

import dagger.Component
import io.ktor.util.*
import pe.isil.mjr.api.controller.AuthController
import pe.isil.mjr.api.controller.ClassroomController
import pe.isil.mjr.api.controller.StudentController
import pe.isil.mjr.api.controller.TeacherController
import javax.inject.Singleton

@KtorExperimentalAPI
@Singleton
@Component
interface ControllerComp {
    fun authController(): AuthController
    fun teacherController(): TeacherController
    fun studentController(): StudentController
    fun classroomController(): ClassroomController
}