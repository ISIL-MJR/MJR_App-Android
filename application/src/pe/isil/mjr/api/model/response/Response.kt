package pe.isil.mjr.api.model.response

interface Response {
    val status: Status
    val message: String
}

enum class Status {
    SUCCESS, NOT_FOUND, FAILED, UNAUTHORIZED
}