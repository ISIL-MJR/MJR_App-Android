package pe.isil.mjr.api

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.util.*
import kotlinx.serialization.json.Json
import pe.isil.mjr.api.auth.AuthJWT
import pe.isil.mjr.api.di.DaggerControllerComp
import pe.isil.mjr.api.exception.FailureMessages
import pe.isil.mjr.api.model.response.FailureResponse
import pe.isil.mjr.api.model.response.Status
import pe.isil.mjr.api.route.auth
import pe.isil.mjr.api.route.classroom
import pe.isil.mjr.api.route.student
import pe.isil.mjr.api.route.teacher
import pe.isil.mjr.api.utils.KeyProvider
import pe.isil.mjr.data.dao.UserDao
import pe.isil.mjr.data.database.initDatabase

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalAPI
fun Application.module() {

    with(Config(environment.config)) {
        initWithSecret(key)
        initDatabase(
            host = host,
            port = port,
            name = name,
            user = user,
            password = password
        )
    }

    install(CORS) {
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        header(HttpHeaders.Authorization)
        allowCredentials = true
        anyHost()
    }

    install(Authentication) {
        jwt {
            verifier(AuthJWT.instance.verifier)
            validate {
                val claim = it.payload.getClaim(AuthJWT.CLAIM).asString()
                if (claim.let(UserDao()::isExists)) {
                    UserIdPrincipal(claim)
                } else {
                    null
                }
            }
        }
    }

    install(StatusPages) {
        exception<BadRequestException> {
            call.respond(
                HttpStatusCode.BadRequest,
                FailureResponse(Status.FAILED, it.message ?: FailureMessages.MESSAGE_FAILED)
            )
        }

        status(HttpStatusCode.Unauthorized) {
            call.respond(
                it,
                FailureResponse(Status.UNAUTHORIZED, FailureMessages.MESSAGE_ACCESS_DENIED)
            )
        }

        status(HttpStatusCode.InternalServerError) {
            call.respond(
                it,
                FailureResponse(Status.FAILED, FailureMessages.MESSAGE_FAILED)
            )
        }
    }

    install(ContentNegotiation) {
        json(
            json = Json {
                prettyPrint = true
            }, contentType = ContentType.Application.Json
        )
    }

    val controllers = DaggerControllerComp.create()

    routing {
        auth(controllers.authController())
        teacher(controllers.teacherController())
        student(controllers.studentController())
        classroom(controllers.classroomController())
    }
}

fun initWithSecret(secretKey: String) {
    AuthJWT.initialize(secretKey)
    KeyProvider.initialize(secretKey)
}