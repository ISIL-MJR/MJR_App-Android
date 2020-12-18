package pe.isil.mjr.api.exception

class NotFoundException(override val message: String) : Exception(message)

class BadRequestException(override val message: String) : Exception(message)

class UnauthorizedException(override val message: String) : Exception(message)