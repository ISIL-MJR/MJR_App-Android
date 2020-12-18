package pe.isil.mjr.api.model.response

import kotlinx.serialization.Serializable

@Serializable
data class FailureResponse(
    override val status: Status,
    override val message: String
) : Response