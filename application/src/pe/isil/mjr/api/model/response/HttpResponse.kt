package pe.isil.mjr.api.model.response

import io.ktor.http.*

sealed class HttpResponse<T : Response> {

    abstract val body: T
    abstract val code: HttpStatusCode

    data class Ok<T : Response>(override val body: T) : HttpResponse<T>() {
        override val code: HttpStatusCode = HttpStatusCode.OK
    }

    data class NotFound<T : Response>(override val body: T) : HttpResponse<T>() {
        override val code: HttpStatusCode = HttpStatusCode.NotFound
    }

    data class BadRequest<T : Response>(override val body: T) : HttpResponse<T>() {
        override val code: HttpStatusCode = HttpStatusCode.BadRequest
    }

    data class Unauthorized<T : Response>(override val body: T) : HttpResponse<T>() {
        override val code: HttpStatusCode = HttpStatusCode.Unauthorized
    }

    companion object {
        fun <T : Response> ok(response: T) = Ok(body = response)
        fun <T : Response> notFound(response: T) = NotFound(body = response)
        fun <T : Response> badRequest(response: T) = BadRequest(body = response)
        fun <T : Response> unauthorized(response: T) = Unauthorized(body = response)
    }
}

fun generateHttpResponse(response: Response): HttpResponse<Response> {
    return when (response.status) {
        Status.SUCCESS -> HttpResponse.ok(response)
        Status.NOT_FOUND -> HttpResponse.notFound(response)
        Status.FAILED -> HttpResponse.badRequest(response)
        Status.UNAUTHORIZED -> HttpResponse.unauthorized(response)
    }
}