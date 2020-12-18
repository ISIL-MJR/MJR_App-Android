package pe.isil.mjr.api.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm

open class AuthJWT(secret: String) {
    private val algorithm = Algorithm.HMAC256(secret)
    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(ISSUER)
        .withAudience(AUDIENCE)
        .build()

    fun sign(id: String): String {
        return JWT
            .create()
            .withSubject("Authentication")
            .withIssuer(ISSUER)
            .withAudience(AUDIENCE)
            .withClaim(CLAIM, id)
            .sign(algorithm)
    }

    companion object {
        lateinit var instance: AuthJWT
            private set

        fun initialize(secret: String) {
            synchronized(this) {
                if (!this::instance.isInitialized) {
                    instance = AuthJWT(secret)
                }
            }
        }

        private const val ISSUER = "MJR-JWT-Issuer"
        private const val AUDIENCE = "https://mjr-dev-api.herokuapp.com"
        const val CLAIM = "id"
    }
}