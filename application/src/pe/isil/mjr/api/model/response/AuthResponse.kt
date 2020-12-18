package pe.isil.mjr.api.model.response

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    override val status: Status,
    override val message: String,
    val token: String? = null
) : Response {

    companion object {

        fun success(message: String, token: String?): AuthResponse {
            return AuthResponse(
                Status.SUCCESS,
                message,
                token
            )
        }

        fun failed(message: String): AuthResponse {
            return AuthResponse(
                Status.FAILED,
                message
            )
        }

        fun unauthorized(message: String): AuthResponse {
            return AuthResponse(
                Status.UNAUTHORIZED,
                message
            )
        }
    }
}