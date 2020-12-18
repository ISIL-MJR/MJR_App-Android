package pe.isil.mjr.api.route

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import pe.isil.mjr.api.controller.AuthController
import pe.isil.mjr.api.exception.BadRequestException
import pe.isil.mjr.api.exception.FailureMessages
import pe.isil.mjr.api.model.request.AuthRequest
import pe.isil.mjr.api.model.response.generateHttpResponse

@KtorExperimentalAPI
fun Route.auth(controller: AuthController) {

    route("/auth") {

        post("/register") {
            val request = runCatching { call.receive<AuthRequest>() }.getOrElse {
                throw BadRequestException(FailureMessages.MESSAGE_MISSING)
            }

            val response = controller.register(request.username, request.password)
            val generate = generateHttpResponse(response)

            call.respond(generate.code, generate.body)
        }

        post("/login") {
            val request = runCatching { call.receive<AuthRequest>() }.getOrElse {
                throw BadRequestException(FailureMessages.MESSAGE_MISSING)
            }

            val response = controller.login(request.username, request.password)
            val generate = generateHttpResponse(response)

            call.respond(generate.code, generate.body)
        }
    }
}