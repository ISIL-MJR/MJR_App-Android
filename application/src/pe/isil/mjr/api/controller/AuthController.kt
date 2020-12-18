package pe.isil.mjr.api.controller

import io.ktor.util.*
import pe.isil.mjr.api.auth.AuthJWT
import pe.isil.mjr.api.exception.BadRequestException
import pe.isil.mjr.api.exception.UnauthorizedException
import pe.isil.mjr.api.model.response.AuthResponse
import pe.isil.mjr.api.utils.hash
import pe.isil.mjr.api.utils.isAlphaNumeric
import pe.isil.mjr.data.dao.UserDao
import javax.inject.Inject

@KtorExperimentalAPI
class AuthController @Inject constructor(private val dao: UserDao) {

    private val jwt = AuthJWT.instance

    fun register(username: String, password: String): AuthResponse {
        return try {
            validateCredentialsOrThrowException(username, password)

            if (!dao.isAvailable(username)) {
                throw BadRequestException("Username is not available")
            }

            dao.add(username, hash(password))
            AuthResponse.success("Registration successful", null)
        } catch (e: BadRequestException) {
            AuthResponse.failed(e.message)
        }
    }

    fun login(username: String, password: String): AuthResponse {
        return try {
            validateCredentialsOrThrowException(username, password)

            val user = dao.get(username, hash(password))
                ?: throw UnauthorizedException("Invalid credentials")

            AuthResponse.success("Login successful", jwt.sign(user.id))
        } catch (e: BadRequestException) {
            AuthResponse.failed(e.message)
        } catch (e: UnauthorizedException) {
            AuthResponse.unauthorized(e.message)
        }
    }

    private fun validateCredentialsOrThrowException(username: String, password: String) {
        val message = when {
            (username.isBlank() or password.isBlank()) -> "Username or password should not be blank"
            (username.length !in (6..30)) -> "Username should be of min 6 and max 30 character in length"
            (password.length !in (6..30)) -> "Password should be of min 6 and max 30 character in length"
            (!username.isAlphaNumeric()) -> "No special characters allowed in username"
            else -> return
        }

        throw BadRequestException(message)
    }
}