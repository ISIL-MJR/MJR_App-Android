package pe.isil.mjr.api.route

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import pe.isil.mjr.api.controller.TeacherController
import pe.isil.mjr.api.exception.BadRequestException
import pe.isil.mjr.api.exception.FailureMessages
import pe.isil.mjr.api.model.request.TeacherRequest
import pe.isil.mjr.api.model.response.generateHttpResponse

@KtorExperimentalAPI
fun Route.teacher(controller: TeacherController) {

    authenticate {

        route("/teacher") {

            get("/all") {
                val response = controller.getAll()
                val generate = generateHttpResponse(response)

                call.respond(generate.code, generate.body)
            }

            get("/{id}") {
                val id = call.parameters["id"] ?: return@get

                val response = controller.get(id)
                val generate = generateHttpResponse(response)

                call.respond(generate.code, generate.body)
            }

            post("/new") {
                val request = runCatching { call.receive<TeacherRequest>() }.getOrElse {
                    throw BadRequestException(FailureMessages.MESSAGE_MISSING)
                }

                val response = controller.add(request)
                val generate = generateHttpResponse(response)

                call.respond(generate.code, generate.body)
            }

            put("/{id}/edit") {
                val id = call.parameters["id"] ?: return@put

                val request = runCatching { call.receive<TeacherRequest>() }.getOrElse {
                    throw BadRequestException(FailureMessages.MESSAGE_MISSING)
                }

                val response = controller.update(id, request)
                val generate = generateHttpResponse(response)

                call.respond(generate.code, generate.body)
            }

            put("/{id}/status") {
                val id = call.parameters["id"] ?: return@put

                val request = runCatching { call.receive<TeacherRequest>() }.getOrElse {
                    throw BadRequestException(FailureMessages.MESSAGE_MISSING)
                }

                val response = controller.status(id, request.status)
                val generate = generateHttpResponse(response)

                call.respond(generate.code, generate.body)
            }

            get("/{id}/classrooms") {
                val id = call.parameters["id"] ?: return@get

                val response = controller.classrooms(id)
                val generate = generateHttpResponse(response)

                call.respond(generate.code, generate.body)
            }
        }
    }
}